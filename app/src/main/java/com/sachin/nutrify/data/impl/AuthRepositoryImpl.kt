package com.sachin.nutrify.data.impl

import com.sachin.nutrify.data.AuthRepository
import com.sachin.nutrify.data.prefs.AuthDataSource
import com.sachin.nutrify.model.User

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun updateAccountDetails(user: User) {
        authDataSource.updateAccountDetails(user)
    }

    override fun getAccountDetails(): User? =
        authDataSource.getAccountDetails()
}
