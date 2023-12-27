package com.sachin.nutrify.ui.auth

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin.nutrify.data.impl.CredentialRepositoryImpl
import com.sachin.nutrify.model.User
import kotlinx.coroutines.launch

class AuthViewModel(
    private val credRepository: CredentialRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableLiveData<AuthState>()

    val authUiState: LiveData<AuthState> = _uiState

    fun init(activity: Activity) {
        viewModelScope.launch {
            credRepository.init(activity)
        }
    }
    fun signUp(activity: Activity, user: User) {
        viewModelScope.launch {
            credRepository.signIn(
                activity,
                user.firstName + " " + user.lastName,
                "12345"
            )
        }
    }

    fun signIn(activity: Activity, id: String, pass: String) {
        viewModelScope.launch {
            credRepository.signIn(activity, id, pass)
        }
    }
}
