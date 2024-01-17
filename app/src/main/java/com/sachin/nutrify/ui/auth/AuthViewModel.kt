package com.sachin.nutrify.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin.nutrify.data.AuthRepository
import com.sachin.nutrify.model.User
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<AuthState>()

    val authUiState: LiveData<AuthState> = _uiState

    fun init(activity: Activity) {
        viewModelScope.launch {
            authRepository.init(activity)
            _uiState.postValue(AuthState.InitSuccess)
        }
    }

    fun signIn(activity: Activity, id: String, pass: String) {
        viewModelScope.launch {
            authRepository.signIn(activity, id, pass)
        }
    }

    fun signInIntent() = authRepository.getSignInIntent()

    fun updateAccount(user: User) {
        viewModelScope.launch {
            authRepository.updateAccountDetails(user)
        }
    }

    fun isUserLoggedIn(context: Context): Boolean =
        authRepository.isLoggedIn(context)

    fun getLoginDetails(data: Intent): User? =
        authRepository.getAccountDetails(data)
}
