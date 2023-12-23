package com.sachin.nutrify.dev

import android.app.Application
import androidx.annotation.Nullable
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import timber.log.Timber
import timber.log.Timber.DebugTree


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        d("App", "App started")
        Timber.plant(object : DebugTree() {
            @Nullable
            override fun createStackElementTag(element: StackTraceElement): String? {
                return super.createStackElementTag(element) + ":" + element.lineNumber
            }
        })
    }
}