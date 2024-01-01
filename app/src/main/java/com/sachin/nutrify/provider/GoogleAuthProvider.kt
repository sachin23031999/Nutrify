package com.sachin.nutrify.provider

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.sachin.nutrify.model.User
import timber.log.Timber.d

class GoogleAuthProvider {
    private val googleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
    }
    private var googleSignInClient: GoogleSignInClient? = null

    fun init(activity: Activity) {
        googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
    }

    fun getSignInIntent(): Intent? = googleSignInClient?.signInIntent

    fun getSignInDetails(data: Intent): User? = try {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        task.getResult(ApiException::class.java)?.let { account ->
            User(
                id = account.id!!,
                firstName = account.displayName!!,
                lastName = "",
                email = account.email!!,
                imageUrl = account.photoUrl?.path,
                federatedToken = account.idToken
            )
        }
    } catch (e: Exception) {
        d("Exception occurred: ${e.localizedMessage}")
        null
    }

    fun signOut() {
        googleSignInClient?.signOut()?.addOnCompleteListener {

        }
    }
}
