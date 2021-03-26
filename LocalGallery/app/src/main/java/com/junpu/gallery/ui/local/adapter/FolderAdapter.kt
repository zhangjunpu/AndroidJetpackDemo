package com.junpu.gallery.ui.local.adapter

import android.view.ViewGroup
import com.junpu.adapter.BaseAdapter
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.OnItemClickListener
import com.junpu.adapter.RecyclerViewHolder
import com.junpu.gallery.databinding.FolderGridItemBinding
import com.junpu.gallery.databinding.FolderListItemBinding
import com.junpu.gallery.ui.local.bean.Folder
import com.junpu.gallery.ui.local.utils.TYPE_IMAGE
import com.junpu.utils.dip
import com.junpu.utils.layoutInflater
import com.junpu.utils.screenWidth
import kotlinx.android.synthetic.main.folder_grid_item.view.*

/**
 * 文件夹
 * @author junpu
 * @date 2020/7/14
 */
class FolderAdapter(private val spanCount: Int, private val type: String?) : BaseAdapter<Folder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        when (type) {
            TYPE_IMAGE -> {
                val binding = FolderGridItemBinding.inflate(
                    parent.layoutInflater,
                    parent,
                    false
                )
                return FolderGridHolder(binding, listener, spanCount)
            }
            else -> {
                val binding = FolderListItemBinding.inflate(
                    parent.layoutInflater,
                    parent,
                    false
                )
                return FolderListHolder(binding, listener)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = getItem(position)
        when (type) {
            TYPE_IMAGE -> (holder as? FolderGridHolder)?.binding?.run {
                folder = item
                executePendingBindings()
            }
            else -> (holder as? FolderListHolder)?.binding?.run {
                folder = item
                executePendingBindings()
            }
        }
    }
}

class FolderGridHolder(
    val binding: FolderGridItemBinding,
    listener: OnItemClickListener?,
    spanCount: Int
) : BaseViewHolder<Folder>(binding.root, listener) {

    init {
        val screenWidth = context.screenWidth
        val space = dip(5) * (spanCount * 2 + 2)
        val width = (screenWidth - space) / spanCount
        itemView.icon.layoutParams.width = width
    }
}

class FolderListHolder(
    val binding: FolderListItemBinding,
    listener: OnItemClickListener?
) : BaseViewHolder<Folder>(binding.root, listener)
