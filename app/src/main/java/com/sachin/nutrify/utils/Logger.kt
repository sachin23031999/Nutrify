package com.sachin.nutrify.utils

import timber.log.Timber

class Logger {
    companion object {
        fun d(tag: String, message: String) {
            Timber.d("nutrifyLog [$tag][${Utils.getLineNumber()}]", message)
        }
        fun e(tag: String, message: String) {
            Timber.e("nutrifyLog [$tag]", message)
        }
        fun i(tag: String, message: String) {
            Timber.i("nutrifyLog [$tag]", message)
        }
    }
}