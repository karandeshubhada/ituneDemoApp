package com.demo.itunesdemoapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        globalContext = this
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var globalContext: Context
    }
}