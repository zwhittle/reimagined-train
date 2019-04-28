package com.awesome.zach.jotunheimrsandbox

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.android.startKoin

class Jotunheimr : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}