package com.sachin.nutrify.data.prefs

import android.content.Context
import com.google.gson.Gson
import com.sachin.nutrify.model.User

class AuthDataSource(context: Context) {
    private val sharedPrefs =
        context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)

    suspend fun updateAccountDetails(user: User) {
        sharedPrefs.edit().putString(
            KEY_ACCOUNT_INFO,
            Gson().toJson(user)
        ).apply()
    }

    fun getAccountDetails(): User? = Gson().fromJson(
        sharedPrefs.getString(KEY_ACCOUNT_INFO, null),
        User::class.java
    )

    companion object {
        private const val SHARED_PREF_FILE_NAME = "auth_info"
        private const val KEY_ACCOUNT_INFO = "account_info"
    }
}
