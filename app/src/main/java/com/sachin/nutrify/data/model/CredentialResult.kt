package com.sachin.nutrify.data.model

import androidx.credentials.PasswordCredential

data class CredentialResult(val credentials: PasswordCredential? = null, val error: Error? = null)

data class Error(val errorMessage: String)