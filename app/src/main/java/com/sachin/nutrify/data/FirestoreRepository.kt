package com.sachin.nutrify.data

import com.sachin.nutrify.model.User
import com.sachin.nutrify.ui.messaging.chat.ChatMessage

interface FirestoreRepository {
    /**
     * Add user on first time login.
     */
    fun addUser(user: User): Boolean

    /**
     * Add messages on send.
     */
    fun addMessage(msg: ChatMessage): Boolean

    /**
     * Get all set messages for current user.
     */
    suspend fun getSentMessages(remoteId: String, deviceUserId: String): List<ChatMessage>

    /**
     * Get all received messages for current user.
     */
    suspend fun getReceivedMessages(deviceUserId: String, remoteId: String): List<ChatMessage>

    /**
     * Get all users.
     */
    suspend fun getAllUsers(): List<User>
}
