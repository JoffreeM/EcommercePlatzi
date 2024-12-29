package com.jop.marketjp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var appContext: Context
        val navigation: MutableMap<String, Any> = mutableMapOf()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}