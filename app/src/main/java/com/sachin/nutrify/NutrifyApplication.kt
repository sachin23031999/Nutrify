package com.sachin.nutrify

import android.app.Application
import android.util.Log
import androidx.annotation.Nullable
import com.sachin.nutrify.injection.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

class NutrifyApplication : Application(), KoinComponent  {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "OnCreate app")
        setupTimber()
        loadKoin(getProcessName())
    }

    private fun loadKoin(processName: String) {
        Log.d(TAG, "Loading koin for process: $processName")
        startKoin {
            androidLogger()
            androidContext(this@NutrifyApplication)
            loadKoinModules(getModules())
        }
    }

    private fun getModules() = listOf(appModule)

    private fun setupTimber() {
        Log.d(TAG, "App started")
        Timber.plant(object : Timber.DebugTree() {
            @Nullable
            override fun createStackElementTag(element: StackTraceElement): String? {
                return super.createStackElementTag(element) + ":" + element.lineNumber
            }
        })
    }

    companion object {
        private val TAG = NutrifyApplication::class.java.simpleName
    }
}
