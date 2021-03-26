package com.junpu.pixabaygallery.ui.gallery

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.junpu.log.L
import com.junpu.pixabaygallery.R
import com.junpu.pixabaygallery.net.LoadStatus
import com.junpu.pixabaygallery.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * 画廊页面
 * @author junpu
 * @date 2020/8/3
 */
class GalleryFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()
    private val adapter by lazy { GalleryAdapter() }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                refreshLayout.isRefreshing = true
                viewModel.invalidateDataSource()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.run {
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                    // 防止位置变换
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }
            adapter = this@GalleryFragment.adapter.also {
                it.doOnRetry {
                    viewModel.retry()
                }
            }
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val spanIndex =
                        (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
                    val space = (resources.displayMetrics.density * 2).toInt()
                    val rect = Rect(0, 0, space, space)
                    if (spanIndex % 2 != 0) rect.right = 0
                    outRect.set(rect)
                }
            })
        }
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            L.ii(viewModel.loadStatus.value)
        })
        viewModel.loadStatus.observe(viewLifecycleOwner, Observer {
            adapter.loadStatus = it
            refreshLayout.isRefreshing = it == LoadStatus.LOADING_INITIAL
        })
        refreshLayout?.setOnRefreshListener {
            viewModel.invalidateDataSource()
        }
    }

}