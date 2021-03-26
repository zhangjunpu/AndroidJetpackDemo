package com.junpu.gallery

import android.Manifest
import android.content.Intent
import com.junpu.gallery.base.BaseActivity
import com.junpu.gallery.ui.main.MainActivity

/**
 * 启动页面
 * @author junpu
 * @date 2020/7/13
 */
class LaunchActivity : BaseActivity() {

    override fun onStart() {
        super.onStart()
        checkPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }
    }
}