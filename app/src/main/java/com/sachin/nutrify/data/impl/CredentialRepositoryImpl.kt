package com.sachin.nutrify.data.impl

import android.app.Activity
import androidx.credentials.*
import androidx.credentials.exceptions.*
import com.sachin.nutrify.data.CredentialRepository
import com.sachin.nutrify.data.model.CredentialResult
import com.sachin.nutrify.data.model.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CredentialRepositoryImpl : CredentialRepository {
    private lateinit var credentialManager: CredentialManager
    private val bgScope = CoroutineScope(Dispatchers.IO)

    fun init(activity: Activity) {
        credentialManager = CredentialManager.create(activity)
    }

    /**
     * Create new credentials with credential manager
     *
     * @param activity - the activity context needed to show the credentials flow dialog to the user
     * @param username - username
     * @param password - password
     * @param coroutineScope - The coroutine scope to run the dialog on
     */
    override suspend fun signIn(
        activity: Activity,
        username: String,
        password: String
    ): CredentialResult {
        return if (isCredentialsSaved(activity) != null) {
            getCredential(activity)
        } else {
            saveCredential(activity, username, password)
        }
    }

    override suspend fun isSignedIn(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logout(username: String): CredentialResult {
        TODO("Not yet implemented")
    }

    /**
     * Fetches saved credentials
     *
     * @return [PasswordCredential] that contains user creds
     */
    private suspend fun getCredential(
        activity: Activity
    ): CredentialResult {
        try {
            val getCredRequest = GetCredentialRequest(listOf(GetPasswordOption()))

            // Shows the user a dialog allowing them to pick a saved credential
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = getCredRequest,
            )
            val credential = credentialResponse.credential as? PasswordCredential

            return CredentialResult(credentials = credential)
        } catch (e: GetCredentialCancellationException) {
            Timber.e("User cancelled the request, $e")
            return CredentialResult(error = Error("User cancelled the request"))
        } catch (e: NoCredentialException) {
            Timber.e("Credentials not found, $e")
            return CredentialResult(error = Error("Credentials not found"))
        } catch (e: GetCredentialException) {
            Timber.e("Error fetching the credentials, $e")
            return CredentialResult(error = Error("Error fetching the credentials"))
        }
    }

    /**
     * Ask the user for permission to add the credentials to their store and saves the credentials to memory
     *
     * @param username - The username for the user
     * @param password - password for the user
     */
    private suspend fun saveCredential(
        activity: Activity,
        username: String,
        password: String
    ): CredentialResult = try {
        val response = credentialManager.createCredential(
            request = CreatePasswordRequest(username, password),
            context = activity,
        )

        Timber.e("Credentials successfully added: ${response.data}")
        CredentialResult(credentials = PasswordCredential(username, password))
    } catch (e: CreateCredentialCancellationException) {
        Timber.e("User cancelled the save flow")
        CredentialResult(error = Error("User cancelled the save flow"))
    } catch (e: CreateCredentialException) {
        Timber.e("Credentials cannot be saved, $e")
        CredentialResult(error = Error("Credentials cannot be saved"))
    }

    private suspend fun isCredentialsSaved(
        activity: Activity
    ): PasswordCredential? = suspendCoroutine { continuation ->
        bgScope.launch {
            try {
                val getCredRequest = GetCredentialRequest(listOf(GetPasswordOption()))

                val credentialResponse = credentialManager.getCredential(
                    context = activity,
                    request = getCredRequest,
                )
                val credential = credentialResponse.credential as? PasswordCredential
                continuation.resume(credential)
            } catch (e: GetCredentialCancellationException) {
                continuation.resume(null)
                Timber.e(e.toString())
            } catch (e: NoCredentialException) {
                continuation.resume(null)
                Timber.e(e.toString())
            } catch (e: GetCredentialException) {
                continuation.resume(null)
                Timber.e(e.toString())
            }
        }
    }
}
