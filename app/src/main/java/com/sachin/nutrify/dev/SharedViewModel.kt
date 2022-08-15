package com.sachin.nutrify.dev

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.model.User

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val fsDbHelper = FirestoreDbHelper(application)
    fun signUp(user: User, signUpCallback: FirestoreDbHelper.SignUpListener) {
        fsDbHelper.addUser(user, signUpCallback)
    }
    fun signIn(id: String, pass: String, callback: FirestoreDbHelper.SignInListener) {
        fsDbHelper.signIn(id, pass, callback)
    }


}