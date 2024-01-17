package com.sachin.nutrify.data.impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.sachin.nutrify.data.AuthRepository
import com.sachin.nutrify.data.model.CredentialResult
import com.sachin.nutrify.data.prefs.AuthDataSource
import com.sachin.nutrify.model.User
import com.sachin.nutrify.provider.CredentialProvider
import com.sachin.nutrify.provider.GoogleAuthProvider

class AuthRepositoryImpl(
    private val googleAuthProvider: GoogleAuthProvider,
    private val credentialProvider: CredentialProvider,
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override fun init(activity: Activity) {
        googleAuthProvider.init(activity)
    }

    override suspend fun signIn(
        activity: Activity,
        username: String,
        password: String
    ): Boolean = credentialProvider
        .signIn(activity, username, password) is CredentialResult.Success

    override fun getSignInIntent(): Intent? =
        googleAuthProvider.getSignInIntent()

    override suspend fun updateAccountDetails(user: User) {
        authDataSource.updateAccountDetails(user)
    }

    override fun getAccountDetails(data: Intent): User? =
        googleAuthProvider.getSignInDetails(data)

    override fun isLoggedIn(context: Context): Boolean =
        googleAuthProvider.isLoggedIn(context)

    override suspend fun logout() {
        googleAuthProvider.signOut()
    }
}
