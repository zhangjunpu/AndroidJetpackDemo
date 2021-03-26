package com.junpu.gallery.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.junpu.gallery.R
import com.junpu.gallery.base.BaseActivity
import com.junpu.gallery.ui.local.LocalActivity
import com.junpu.gallery.ui.local.utils.TYPE_AUDIO
import com.junpu.gallery.ui.local.utils.TYPE_IMAGE
import com.junpu.gallery.ui.local.utils.TYPE_VIDEO
import com.junpu.utils.launch

/**
 * 主页面
 * @author junpu
 * @date 2020/7/13
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun launchLocal(view: View) {
        val type = when (view.id) {
            R.id.btnLocalImage -> TYPE_IMAGE
            R.id.btnLocalVideo -> TYPE_VIDEO
            R.id.btnLocalAudio -> TYPE_AUDIO
            else -> TYPE_IMAGE
        }
        launch(LocalActivity::class.java, bundleOf(LocalActivity.ARGS_TYPE to type))
    }

}