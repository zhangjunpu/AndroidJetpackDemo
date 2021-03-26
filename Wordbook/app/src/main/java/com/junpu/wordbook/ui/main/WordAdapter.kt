package com.junpu.wordbook.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.junpu.wordbook.databinding.WordItemBinding
import com.junpu.wordbook.db.Word
import kotlinx.android.synthetic.main.word_item.view.*

/**
 *
 * @author junpu
 * @date 2020/7/23
 */
class WordAdapter : ListAdapter<Word, WordAdapter.WordViewHolder>(
    object : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.englishName == newItem.englishName &&
                    oldItem.chineseName == newItem.chineseName &&
                    oldItem.grasp == newItem.grasp
        }
    }) {

    private var onItemClickListener: ((RecyclerView?, View?, position: Int) -> Unit)? = null
    private var onCheckedListener: ((Boolean, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WordItemBinding.inflate(inflater, parent, false)
        return WordViewHolder(
            binding,
            onItemClickListener,
            onCheckedListener
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: WordViewHolder) {
        holder.itemView.textId?.text = (holder.adapterPosition + 1).toString()
    }

    val item = { position: Int -> getItem(position) }

    fun doOnItemClick(listener: (RecyclerView?, View?, position: Int) -> Unit) {
        this.onItemClickListener = listener
    }

    fun doOnSwitchChanged(listener: (isChecked: Boolean, position: Int) -> Unit) {
        this.onCheckedListener = listener
    }

    /**
     * ViewHolder
     * @author junpu
     * @date 2020/7/23
     */
    class WordViewHolder(
        private val binding: WordItemBinding,
        private val listener: ((RecyclerView?, View?, position: Int) -> Unit)? = null,
        private val checkedListener: ((Boolean, Int) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.run {
                setOnClickListener {
                    listener?.invoke(itemView.parent as? RecyclerView, it, adapterPosition)
                }
                btnSwitch.setOnCheckedChangeListener { _, isChecked ->
                    textChinese.visibility = if (isChecked) View.GONE else View.VISIBLE
                    checkedListener?.invoke(isChecked, adapterPosition)
                }
            }
        }

        fun bindData(word: Word) {
            binding.run {
                textId.text = (adapterPosition + 1).toString()
                this.word = word
                this.executePendingBindings()
            }
        }
    }
}