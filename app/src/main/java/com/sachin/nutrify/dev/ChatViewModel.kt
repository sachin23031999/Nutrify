package com.sachin.nutrify.dev

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.model.ChatMessage
import com.sachin.nutrify.dev.model.UserInfo
import com.sachin.nutrify.dev.utils.Logger.Companion.d

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val fireDb = FirestoreDbHelper(application)
    private val TAG = ChatViewModel::class.java.simpleName

    fun sendMessage(msg: ChatMessage) {
        d(TAG, "sendMessage()")
        fireDb.sendMessage(msg)
    }

    fun getMessages(rxId: String, action: FirestoreDbHelper.GetMessagesListener) {
        fireDb.getMessagesByRxId(rxId, action)
    }

    fun getAllUsers(action: FirestoreDbHelper.getUsersListener): MutableList<UserInfo> {
        var result = mutableListOf<UserInfo>()
        fireDb.getAllUsers(action)
        return result
    }
}