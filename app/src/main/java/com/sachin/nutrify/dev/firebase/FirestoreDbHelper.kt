package com.sachin.nutrify.dev.firebase

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.nutrify.dev.model.User
import com.sachin.nutrify.dev.utils.Constants
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.SharedPrefHelper

class FirestoreDbHelper(context: Context) {
    private var fireBaseDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var pref: SharedPrefHelper = SharedPrefHelper.getInstance(context)
    private var TAG = FirestoreDbHelper::class.java.simpleName

    fun addUser(user: User, signUpCallback: SignUpListener) {
        val dataMap = HashMap<String, Any>()
        dataMap[Constants.FIRST_NAME] = user.firstName
        dataMap[Constants.LAST_NAME] = user.lastName
        dataMap[Constants.EMAIL] = user.email
        dataMap[Constants.PASSWORD] = user.password
        dataMap[Constants.IMAGE] = user.encodedImage.toString()
        putData(Constants.Database.USERS, dataMap, signUpCallback)
    }


    private fun putData(key: String, data: HashMap<String, Any>, signUpCallback: SignUpListener) {
        fireBaseDb.collection(key)
            .add(data)
            .addOnSuccessListener {
                d(TAG, "User added")
                signUpCallback.signUpSuccess()
            }
            .addOnFailureListener {
                d(TAG, "User not added, please try again")
                signUpCallback.signUpFailure()
            }
    }

    interface SignInListener {
        fun signInSuccess()
        fun signInFailure()
    }

    interface SignUpListener {
        fun signUpSuccess()
        fun signUpFailure()
    }
    fun signIn(id: String, pass: String, callback: SignInListener) {
        fireBaseDb.collection(Constants.Database.USERS)
            .whereEqualTo(Constants.EMAIL, id)
            .whereEqualTo(Constants.PASSWORD, pass)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful && task.result != null
                    && task.result.documents.size > 0) {
                    val snapshot = task.result.documents[0]
                    pref.putBoolean(Constants.IS_SIGNED_IN, true)
                    callback.signInSuccess()
                }
            }
            .addOnFailureListener {
                callback.signInFailure()
            }
    }

}