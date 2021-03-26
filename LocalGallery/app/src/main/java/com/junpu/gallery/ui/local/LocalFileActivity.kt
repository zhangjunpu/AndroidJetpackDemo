package com.junpu.gallery.ui.local

import android.os.Bundle
import com.junpu.gallery.R
import com.junpu.gallery.base.BaseActivity
import com.junpu.gallery.ui.local.bean.Folder
import kotlinx.android.synthetic.main.activity_local_file.*

/**
 * 本地文件
 * @author junpu
 * @date 2020/7/14
 */
class LocalFileActivity : BaseActivity() {

    companion object {
        const val ARGS_FOLDER = "args_folder"
    }

    private var folder: Folder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_file)
        folder = intent.getParcelableExtra(ARGS_FOLDER)
        initTitleBar()
    }

    private fun initTitleBar() {
        toolbar?.run {
            title = getString(R.string.format_folder_name).format(folder?.name, folder?.list?.size ?: 0)
            setNavigationOnClickListener { finish() }
        }
    }
}