package com.sachin.nutrify.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin.nutrify.data.CredentialRepository
import com.sachin.nutrify.data.AuthRepository
import com.sachin.nutrify.model.User
import com.sachin.nutrify.provider.GoogleAuthProvider
import kotlinx.coroutines.launch

class AuthViewModel(
    private val credRepository: CredentialRepository,
    private val authRepository: AuthRepository,
    private val googleAuthProvider: GoogleAuthProvider
) : ViewModel() {

    private val _uiState = MutableLiveData<AuthState>()

    val authUiState: LiveData<AuthState> = _uiState

    fun init(activity: Activity) {
        viewModelScope.launch {
            googleAuthProvider.init(activity)
            _uiState.postValue(AuthState.InitSuccess)
        }
    }

    fun signIn(activity: Activity, id: String, pass: String) {
        viewModelScope.launch {
            credRepository.signIn(activity, id, pass)
        }
    }

    fun signInIntent() = googleAuthProvider.getSignInIntent()

    fun updateAccount(user: User) {
        viewModelScope.launch {
            authRepository.updateAccountDetails(user)
        }
    }

    fun isUserLoggedIn(): Boolean =
        authRepository.getAccountDetails() != null
    fun getLoginDetails(data: Intent): User? =
        googleAuthProvider.getSignInDetails(data)
}
