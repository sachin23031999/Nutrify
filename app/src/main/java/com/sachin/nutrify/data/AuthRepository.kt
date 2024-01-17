package com.sachin.nutrify.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.sachin.nutrify.model.User

interface AuthRepository {
    /**
     * Initialise the repository.
     */
    fun init(activity: Activity)

    /**
     * Normal sign in.
     */
    suspend fun signIn(
        activity: Activity,
        username: String,
        password: String
    ): Boolean

    /**
     * Get google sign in intent.
     */
    fun getSignInIntent(): Intent?

    /**
     * Update account details.
     */
    suspend fun updateAccountDetails(user: User)

    /**
     * Get account details.
     */
    fun getAccountDetails(data: Intent): User?

    /**
     * Check if user logged in.
     */
    fun isLoggedIn(context: Context): Boolean

    /**
     * Initiate logout.
     */
    suspend fun logout()
}
