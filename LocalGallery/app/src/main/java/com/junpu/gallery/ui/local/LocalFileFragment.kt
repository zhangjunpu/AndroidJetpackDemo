package com.junpu.gallery.ui.local

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.junpu.adapter.BaseAdapter
import com.junpu.gallery.R
import com.junpu.gallery.base.BaseFragment
import com.junpu.gallery.ui.local.bean.Folder
import com.junpu.gallery.ui.local.bean.LocalFile
import com.junpu.gallery.ui.local.adapter.LocalAudioAdapter
import com.junpu.gallery.ui.local.adapter.LocalImageAdapter
import com.junpu.gallery.ui.local.adapter.LocalVideoAdapter
import com.junpu.gallery.ui.local.utils.TYPE_IMAGE
import com.junpu.gallery.ui.local.utils.TYPE_VIDEO
import com.junpu.gallery.ui.local.utils.localFileInfo
import com.junpu.log.L
import kotlinx.android.synthetic.main.fragment_local_file.*

/**
 * 本地文件
 * @author junpu
 * @date 2020/7/14
 */
class LocalFileFragment : BaseFragment() {

    private var adapter: BaseAdapter<LocalFile>? = null
    private var folder: Folder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        folder = activity?.intent?.getParcelableExtra(LocalFileActivity.ARGS_FOLDER) as? Folder
        L.vv(folder)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_local_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spanCount = resources.getInteger(R.integer.image_span_count)
        adapter = when (folder?.type) {
            TYPE_IMAGE -> LocalImageAdapter(spanCount)
            TYPE_VIDEO -> LocalVideoAdapter()
            else -> LocalAudioAdapter()
        }.apply {
            doOnItemClick { _, _, position ->
                getItem(position)?.let {
//                    toast(it.toString())
                    AlertDialog.Builder(context)
                        .setMessage(localFileInfo(it))
                        .show()
                }
            }
        }
        recyclerView?.run {
            layoutManager = when (folder?.type) {
                TYPE_IMAGE -> GridLayoutManager(context, spanCount)
                else -> LinearLayoutManager(context)
            }
            adapter = this@LocalFileFragment.adapter
        }

        folder?.list?.let { adapter?.update(it) }
    }
}