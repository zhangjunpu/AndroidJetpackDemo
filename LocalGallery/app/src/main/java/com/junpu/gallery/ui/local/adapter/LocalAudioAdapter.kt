package com.junpu.gallery.ui.local.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.junpu.adapter.BaseAdapter
import com.junpu.adapter.BaseViewHolder
import com.junpu.adapter.OnItemClickListener
import com.junpu.adapter.RecyclerViewHolder
import com.junpu.gallery.R
import com.junpu.gallery.databinding.FragmentLocalAudioItemBinding
import com.junpu.gallery.ui.local.bean.LocalFile
import com.junpu.utils.layoutInflater

/**
 * 本地视频
 * @author junpu
 * @date 2020/7/16
 */
class LocalAudioAdapter : BaseAdapter<LocalFile>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<FragmentLocalAudioItemBinding>(
            parent.layoutInflater,
            R.layout.fragment_local_audio_item,
            parent,
            false
        )
        return LocalAudioHolder(
            binding,
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder as LocalAudioHolder
        holder.binding.run {
            file = item
            executePendingBindings()
        }
    }
}

class LocalAudioHolder(
    val binding: FragmentLocalAudioItemBinding,
    listener: OnItemClickListener?
) : BaseViewHolder<LocalFile>(binding.root, listener)