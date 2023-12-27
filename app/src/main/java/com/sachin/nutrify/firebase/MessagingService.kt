package com.sachin.nutrify.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sachin.nutrify.utils.Logger.Companion.d

class MessagingService : FirebaseMessagingService() {

    private val TAG = MessagingService::class.java.simpleName
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        d(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        d(TAG, "onMessageReceived: ${message.notification?.body}")
    }
}