package com.sachin.nutrify

import androidx.lifecycle.ViewModel
import com.sachin.nutrify.data.CredentialRepository

class MainViewModel(
    private val credRepository: CredentialRepository
) : ViewModel() {

    fun isSignedIn(): Boolean {
        return false
    }
}
