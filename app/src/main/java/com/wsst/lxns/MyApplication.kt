package com.wsst.lxns

import android.app.Application
import com.wsst.lxns.utils.ProxyMethod

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ProxyMethod.initPreferences(this)
    }
}