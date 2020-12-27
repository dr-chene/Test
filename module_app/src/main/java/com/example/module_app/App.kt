package com.example.module_app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.isDebug

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        if (isDebug) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}