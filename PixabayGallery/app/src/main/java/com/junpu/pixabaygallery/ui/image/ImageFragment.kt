package com.junpu.pixabaygallery.ui.image

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.junpu.pixabaygallery.R
import com.junpu.pixabaygallery.databinding.FragmentImageBinding
import com.junpu.pixabaygallery.ui.MainViewModel
import com.junpu.toast.toast
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.fragment_image_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val IMAGE_INDEX = "image_index"
const val REQUEST_PERMISSION_CODE = 1

class ImageFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentImageBinding>(
            inflater,
            R.layout.fragment_image,
            container,
            false
        ).apply {
            vm = viewModel.apply { imageIndex.value = arguments?.getInt(IMAGE_INDEX) }
            lifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager.run {
            adapter = ImageAdapter().apply {
                submitList(viewModel.imageList.value)
            }
            setCurrentItem(viewModel.imageIndex.value ?: 0, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.imageIndex.value = position + 1
                }
            })
            orientation = ViewPager2.ORIENTATION_VERTICAL
        }

        imageDownload.setOnClickListener {
            val hasPermission = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                lifecycleScope.launch {
                    savePhoto { toastResult(it) }
                }
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            lifecycleScope.launch { savePhoto { toastResult(it) } }
        } else {
            toastResult(false)
        }
    }

    private suspend fun savePhoto(callback: (Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            val holder =
                (viewPager[0] as RecyclerView).findViewHolderForAdapterPosition(viewPager.currentItem)
            holder as ImageViewHolder
            val bitmap = holder.itemView.photoView.drawable.toBitmap()
            requireContext().contentResolver.run {
                val imageUri = insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    ContentValues()
                ) ?: kotlin.run {
                    MainScope().launch { callback(false) }
                    return@withContext
                }
                openOutputStream(imageUri).use {
                    val result = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
                    MainScope().launch { callback(result) }
                }
            }
        }
    }

    private fun toastResult(isSuccess: Boolean) = toast(if (isSuccess) "保存成功" else "保存失败")

}