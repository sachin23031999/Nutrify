package com.sachin.nutrify

import android.app.Application
import android.content.Context
import androidx.annotation.Nullable
import com.sachin.nutrify.injection.appModule
import com.sachin.nutrify.utils.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

class NutrifyApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        loadKoin(getProcessName())
    }

    private fun loadKoin(processName: String) {
        setupTimber()
        Timber.d("Loading koin for process: $processName")
        startKoin {
            androidLogger()
            androidContext(this@NutrifyApplication)
            loadKoinModules(getNutrifyModules())
        }
    }

    private fun getNutrifyModules() = listOf(appModule)

    private fun setupTimber() {
        Logger.d(TAG, "App started")
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
