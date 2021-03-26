package com.junpu.pixabaygallery

import android.app.Application
import com.junpu.log.L
import com.junpu.toast.toastContext

/**
 *
 * @author junpu
 * @date 2020/7/29
 */
class App : Application() {
    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        toastContext = this
        L.logEnable = BuildConfig.DEBUG
    }
}