package com.junpu.gallery.ui.local

import android.os.Bundle
import com.junpu.gallery.R
import com.junpu.gallery.base.BaseActivity
import com.junpu.gallery.ui.local.utils.localTitleResources
import kotlinx.android.synthetic.main.activity_local.*

/**
 * 本地文件夹
 * @author junpu
 * @date 2020/7/14
 */
class LocalActivity : BaseActivity() {

    companion object {
        const val ARGS_TYPE = "args_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local)
        setTitleBar()
    }

    private fun setTitleBar() {
        val type = intent.getStringExtra(ARGS_TYPE)
        toolbar?.run {
            setTitle(localTitleResources(type))
            setNavigationOnClickListener { finish() }
        }
    }
}