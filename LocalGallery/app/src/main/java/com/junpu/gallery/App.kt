package com.junpu.gallery

import android.app.Application
import com.junpu.log.L
import com.junpu.toast.toastContext
import com.junpu.utils.app

/**
 *
 * @author junpu
 * @date 2020/7/13
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        L.logEnable = BuildConfig.DEBUG
        toastContext = this
    }
}