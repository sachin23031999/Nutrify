package com.sachin.nutrify.data

import com.sachin.nutrify.model.User

interface AuthRepository {

    suspend fun updateAccountDetails(user: User)

    fun getAccountDetails(): User?
}
