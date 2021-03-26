package com.junpu.pixabaygallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.junpu.pixabaygallery.R
import com.junpu.pixabaygallery.bean.ImageBean
import com.junpu.pixabaygallery.databinding.FooterViewBinding
import com.junpu.pixabaygallery.databinding.FragmentGalleryItemBinding
import com.junpu.pixabaygallery.net.LoadStatus
import com.junpu.pixabaygallery.ui.image.IMAGE_INDEX
import kotlinx.android.synthetic.main.fragment_gallery_item.view.*

/**
 * GalleryAdapter
 * @author junpu
 * @date 2020/7/29
 */
class GalleryAdapter : PagedListAdapter<ImageBean, RecyclerView.ViewHolder>(DIFF) {
    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ImageBean>() {
            override fun areItemsTheSame(oldItem: ImageBean, newItem: ImageBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageBean, newItem: ImageBean): Boolean {
                return oldItem == newItem
            }
        }

        private const val ITEM_TYPE_NORMAL = 0
        private const val ITEM_TYPE_FOOTER = 1
    }

    private var retryListener: (() -> Unit)? = null
    private var hasFooter = false
        set(value) {
            if (value) {
                if (field) {
                    notifyItemChanged(itemCount - 1)
                } else {
                    // 这两句写反会导致崩溃，个人理解：hasFooter影响着itemCount，如不先赋值而是直接notify会导致下标越界
                    field = value
                    notifyItemInserted(itemCount - 1)
                }
            } else {
                if (field) {
                    field = value
                    notifyItemRemoved(itemCount - 1)
                }
            }
        }

    var loadStatus: LoadStatus? = null
        set(value) {
            field = value
            hasFooter = value != LoadStatus.LOADING_INITIAL
        }

    fun doOnRetry(listener: () -> Unit) {
        retryListener = listener
        retryListener?.invoke()
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasFooter && position == itemCount - 1) ITEM_TYPE_FOOTER else ITEM_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_FOOTER -> FooterViewHolder.create(parent, retryListener)
            else -> GalleryViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_TYPE_NORMAL) {
            holder as GalleryViewHolder
            holder.bindData(getItem(position))
        } else {
            holder as FooterViewHolder
            holder.bind(loadStatus)
        }
    }

}

/**
 * GalleryViewHolder
 * @author junpu
 * @date 2020/7/29
 */
class GalleryViewHolder(private val binding: FragmentGalleryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = GalleryViewHolder(
            FragmentGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.imageView.setOnClickListener {
                val navController = it.findNavController()
                navController.navigate(
                    R.id.action_galleryFragment_to_imageFragment,
                    bundleOf(IMAGE_INDEX to adapterPosition)
                )
            }
        }
    }

    fun bindData(image: ImageBean?) {
        binding.data = image
        binding.executePendingBindings()
//        itemView.imageView.layoutParams.height = image?.webformatHeight ?: ViewGroup.LayoutParams.WRAP_CONTENT
    }
}

/**
 * GalleryFooter
 * @author junpu
 * @date 2020/7/31
 */
class FooterViewHolder(private val binding: FooterViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup, retry: (() -> Unit)? = null) = FooterViewHolder(
            FooterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                root.layoutParams.let {
                    it as StaggeredGridLayoutManager.LayoutParams
                    it.isFullSpan = true
                }
            }
        ).apply {
            itemView.setOnClickListener {
                retry?.invoke()
            }
        }
    }

    fun bind(loadStatus: LoadStatus?) {
        binding.loadStatus = loadStatus
        binding.executePendingBindings()
    }
}