package com.sachin.nutrify.dev.utils

import android.content.Context
import android.util.Log

class Logger {
    companion object {
        fun d(tag: String, message: String) {
            Log.d("[$tag]", message)
        }
        fun e(tag: String, message: String) {
            Log.e("[$tag]", message)
        }
        fun i(tag: String, message: String) {
            Log.i("[$tag]", message)
        }
    }
}