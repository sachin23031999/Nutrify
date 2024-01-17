package com.sachin.nutrify.provider

import android.app.Activity
import com.sachin.nutrify.data.model.CredentialResult

interface CredentialProvider {
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
    suspend fun logout(): CredentialResult
}
