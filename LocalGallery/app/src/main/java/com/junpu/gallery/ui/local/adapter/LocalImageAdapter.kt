package com.junpu.gallery.ui.local.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.junpu.adapter.BaseAdapter
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.OnItemClickListener
import com.junpu.adapter.RecyclerViewHolder
import com.junpu.gallery.BR
import com.junpu.gallery.R
import com.junpu.gallery.databinding.FragmentLocalImageItemBinding
import com.junpu.gallery.ui.local.bean.LocalFile
import com.junpu.utils.dip
import com.junpu.utils.layoutInflater
import com.junpu.utils.screenWidth
import kotlinx.android.synthetic.main.fragment_local_image_item.view.*

/**
 * 本地相册
 * @author junpu
 * @date 2020/7/14
 */
class LocalImageAdapter(private val spanCount: Int) : BaseAdapter<LocalFile>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<FragmentLocalImageItemBinding>(
            parent.layoutInflater,
            R.layout.fragment_local_image_item,
            parent,
            false
        )
        return LocalImageHolder(
            binding,
            listener,
            spanCount
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder as LocalImageHolder
        holder.binding.run {
            setVariable(BR.file, item)
            executePendingBindings()
        }
    }
}

class LocalImageHolder(
    val binding: FragmentLocalImageItemBinding,
    listener: OnItemClickListener?,
    spanCount: Int
) : BaseViewHolder<LocalFile>(binding.root, listener) {

    init {
        val screenWidth = context.screenWidth
        val space = dip(1) * 2 * (spanCount + 1)
        val width = (screenWidth - space) / spanCount
        itemView.icon.layoutParams.let {
            it.width = width
            it.height = width
        }
    }
}