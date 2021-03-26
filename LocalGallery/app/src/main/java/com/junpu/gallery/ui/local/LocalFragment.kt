package com.junpu.gallery.ui.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.junpu.gallery.R
import com.junpu.gallery.base.BaseFragment
import com.junpu.gallery.databinding.FragmentLocalBinding
import com.junpu.gallery.ui.local.adapter.FolderAdapter
import com.junpu.gallery.ui.local.utils.TYPE_IMAGE
import com.junpu.utils.launch
import kotlinx.android.synthetic.main.fragment_local.*

/**
 * 本地文件夹
 * @author junpu
 * @date 2020/7/14
 */
class LocalFragment : BaseFragment() {

    private val viewModel: LocalViewModel by viewModels()
    private var adapter: FolderAdapter? = null
    private var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLocalBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = this@LocalFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = activity?.intent?.getStringExtra(LocalActivity.ARGS_TYPE)
        viewModel.setType(type)
        val spanCount = resources.getInteger(R.integer.folder_span_count)
        adapter = FolderAdapter(spanCount, type).apply {
            doOnItemClick { _, _, position ->
                getItem(position)?.let {
                    launch(
                        LocalFileActivity::class.java,
                        bundleOf(LocalFileActivity.ARGS_FOLDER to it)
                    )
                }
            }
        }
        recyclerView?.run {
            layoutManager = if (type == TYPE_IMAGE) GridLayoutManager(
                context,
                spanCount
            ) else LinearLayoutManager(context)
            adapter = this@LocalFragment.adapter
        }
        viewModel.folders.observe(viewLifecycleOwner, Observer {
            adapter?.update(it)
        })
        viewModel.getFolders()
    }

}