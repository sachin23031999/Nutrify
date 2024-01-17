package com.sachin.nutrify.data.model

import androidx.credentials.Credential
import androidx.credentials.PasswordCredential

sealed class CredentialResult {
    data class Success(
        val credentials: PasswordCredential? = null,
        val error: Error? = null
    ) : CredentialResult()

    data class Error(val errorMessage: String) : CredentialResult()
}