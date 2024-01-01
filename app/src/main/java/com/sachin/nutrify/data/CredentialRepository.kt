package com.sachin.nutrify.data

import android.app.Activity
import com.sachin.nutrify.data.model.CredentialResult

interface CredentialRepository {
    /**
     * Init.
     */
    fun init(activity: Activity)

    /**
     * Sign in user.
     */
    suspend fun signIn(
        activity: Activity,
        username: String,
        password: String
    ): CredentialResult

    /**
     * Check if signed in.
     */
    suspend fun isSignedIn(username: String): Boolean

    /**
     * Logout user.
     */
    suspend fun logout(username: String): CredentialResult
}
