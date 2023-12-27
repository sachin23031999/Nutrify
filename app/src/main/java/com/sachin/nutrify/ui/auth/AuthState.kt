package com.sachin.nutrify.ui.auth

sealed class AuthState {
    object InitSuccess : AuthState()
    object InitFailure : AuthState()
    object AlreadyExist : AuthState()
    object LoginSuccess : AuthState()
    object SignupSuccess : AuthState()
    object LoginFailure : AuthState()
    object SignupFailure : AuthState()
}