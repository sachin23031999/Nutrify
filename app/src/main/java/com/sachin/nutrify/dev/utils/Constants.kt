package com.sachin.nutrify.dev.utils

class Constants {

    companion object {
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val IMAGE = "encoded_image"
        const val TIME_STAMP = "time_stamp"
        const val IS_SIGNED_IN = "signed_in"
    }
    interface Database {
        companion object {
            const val USERS = "users"
        }
    }
}