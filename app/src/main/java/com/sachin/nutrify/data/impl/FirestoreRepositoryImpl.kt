package com.sachin.nutrify.data.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.nutrify.data.FirestoreRepository
import com.sachin.nutrify.model.User
import com.sachin.nutrify.ui.messaging.chat.ChatMessage
import com.sachin.nutrify.utils.Constants
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.collections.HashMap

class FirestoreRepositoryImpl : FirestoreRepository {
    private val fireBaseDb by lazy { FirebaseFirestore.getInstance() }

    override fun addUser(user: User): Boolean = runBlocking {
        val dataMap = HashMap<String, Any>()
        dataMap[KEY_USER_ID] = user.id
        dataMap[KEY_FIRST_NAME] = user.firstName
        dataMap[KEY_LAST_NAME] = user.lastName
        dataMap[KEY_EMAIL] = user.email
        dataMap[KEY_ENC_IMAGE] = user.imageUrl.toString()
        return@runBlocking putData(COLLECTION_USERS, dataMap)
    }

    override fun addMessage(msg: ChatMessage): Boolean = runBlocking {
        val map = HashMap<String, Any>()
        map[KEY_SENDER_ID] = msg.senderId
        map[KEY_RECEIVER_ID] = msg.receiverId
        map[KEY_MESSAGE] = msg.message
        map[KEY_TIMESTAMP] = msg.tms
        return@runBlocking putData(COLLECTION_CHATS, map)
    }

    private suspend fun getMessages(deviceUserId: String, remoteId: String): List<ChatMessage> =
        fireBaseDb.collection(COLLECTION_CHATS)
            .whereEqualTo(KEY_RECEIVER_ID, remoteId)
            .whereEqualTo(KEY_SENDER_ID, deviceUserId)
            .get().await()
            .documents.map { document ->
                val chatMessage = ChatMessage(
                    document.getString(KEY_SENDER_ID)!!,
                    document.getString(KEY_RECEIVER_ID)!!,
                    document.getString(KEY_MESSAGE)!!,
                    document.getString(KEY_TIMESTAMP)!!,
                )
                chatMessage
            }

    override suspend fun getSentMessages(
        remoteId: String,
        deviceUserId: String,
    ): List<ChatMessage> =
        getMessages(remoteId, deviceUserId)

    override suspend fun getReceivedMessages(
        deviceUserId: String,
        remoteId: String
    ): List<ChatMessage> =
        getMessages(remoteId, deviceUserId)

    private suspend fun putData(
        key: String,
        data: HashMap<String, Any>,
    ): Boolean =
        try {
            fireBaseDb.collection(key).add(data).await()
            true
        } catch (e: Exception) {
            false
        }

    override suspend fun getAllUsers(): List<User> =
        try {
            val querySnapshot = fireBaseDb.collection(COLLECTION_USERS).get().await()
            val users = querySnapshot.documents.map { document ->
                User(
                    document.id,
                    document.getString(Constants.FIRST_NAME)!!,
                    document.getString(Constants.LAST_NAME)!!,
                    document.getString(Constants.EMAIL)!!,
                    document.getString(Constants.ENC_IMAGE)!!,
                )
            }
            users
        } catch (e: Exception) {
            Timber.e("Failed to retrieve users")
            emptyList()
        }

    companion object {
        // Users collection keys
        const val KEY_USER_ID = "user_id"
        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_EMAIL = "email"
        const val KEY_ENC_IMAGE = "encoded_image"

        const val KEY_SENDER_ID = "sender_id"
        const val KEY_RECEIVER_ID = "receiver_id"
        const val KEY_MESSAGE = "message"
        const val KEY_TIMESTAMP = "tms"

        const val COLLECTION_USERS = "users"
        const val COLLECTION_CHATS = "chat"
        const val COLLECTION_CALLS = "calls"

        const val KEY_TYPE = "type"
    }
}

interface DataUpdateListener {
    fun onSuccess()
    fun onFailure()
}
