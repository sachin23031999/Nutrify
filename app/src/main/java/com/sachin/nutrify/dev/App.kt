package com.sachin.nutrify.dev

import android.app.Application
import com.sachin.nutrify.dev.utils.Logger.Companion.d

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        d("App", "App started")
    }
}