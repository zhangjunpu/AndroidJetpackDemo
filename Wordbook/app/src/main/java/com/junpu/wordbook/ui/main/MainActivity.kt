package com.junpu.wordbook.ui.main

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.junpu.log.L
import com.junpu.wordbook.R
import com.junpu.wordbook.db.Word
import com.junpu.wordbook.dialog.AddWordDialog
import com.junpu.wordbook.generateWords
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.word_item.view.*

/**
 *
 * @author junpu
 * @date 2020/7/23
 */
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<WordViewModel>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { WordAdapter() }
    private val dialog by lazy(LazyThreadSafetyMode.NONE) {
        AddWordDialog.Builder(this)
            .doOnSubmit { _, word -> viewModel.addWord(word) }
            .create()
    }
    private val observer = Observer { it: List<Word> ->
        if (adapter.itemCount != it.size) {
            adapter.submitList(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter.apply {
                doOnItemClick { _, _, position ->
                    item(position)?.let {
                        val uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=${it.englishName}")
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                    }
                }
                doOnSwitchChanged { isChecked, position ->
                    item(position)?.let {
                        it.grasp = if (isChecked) 1 else 0
                        viewModel.updateWord(it)
                    }
                }
            }
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                private val space = (resources.displayMetrics.density * 1).toInt()
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    super.onDraw(c, parent, state)

                    parent.children.forEach {
                        val left = it.textEnglish.left
                        val top = it.bottom - space
                        val right = it.right
                        val bottom = it.bottom
                        c.drawRect(
                            Rect(left, top, right, bottom),
                            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                color = ContextCompat.getColor(parent.context, R.color.text_e3)
                                strokeWidth = space.toFloat()
                            })
                    }

                }
            })
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    this@MainActivity.adapter.item(position)?.let { word ->
                        viewModel.deleteWord(word)
                        Snackbar.make(
                            viewHolder.itemView,
                            context.getString(R.string.restore_delete_word),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction(context.getString(R.string.restore)) {
                                viewModel.addWord(word)
                            }
                            .show()
                    }
                }
            }).attachToRecyclerView(this)
            itemAnimator = object: DefaultItemAnimator() {
                override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
                    val layoutManager = layoutManager as LinearLayoutManager
                    val first = layoutManager.findFirstVisibleItemPosition()
                    val last = layoutManager.findLastVisibleItemPosition()
                    for (position in first..last) {
                        (findViewHolderForAdapterPosition(position) as? WordAdapter.WordViewHolder)?.run {
                            itemView.textId?.text = (position + 1).toString()
                        }
                    }
                }
            }
        }
        if (viewModel.curWords == null) viewModel.curWords = viewModel.allWords
        viewModel.curWords?.observe(this, observer)
        initToolbar()
        initView()
    }

    private fun initToolbar() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_clear -> {
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.clear_all_msg))
                        .setNegativeButton(R.string.ok) { _, _ ->
                            viewModel.clear()
                        }
                        .setPositiveButton(R.string.cancel, null)
                        .show()
                }
                R.id.menu_generate -> {
                    viewModel.addWord(*generateWords)
                }
            }

            true
        }
        findViewById<SearchView>(R.id.menu_search).apply {
            maxWidth = (resources.displayMetrics.widthPixels * 0.68).toInt()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.curWords?.removeObservers(this@MainActivity)
                    viewModel.curWords = viewModel.fuzzySearchWords(p0?.trim())
                    viewModel.curWords?.observe(this@MainActivity, observer)
                    return true
                }
            })
        }

    }

    private fun initView() {
        floatingActionButton?.setOnClickListener {
            dialog.show()
        }
    }
}