package com.sachin.nutrify.dev

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.model.ChatMessage
import com.sachin.nutrify.dev.model.FUser
import com.sachin.nutrify.dev.model.UserInfo
import com.sachin.nutrify.dev.utils.Logger.Companion.d

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val fireDb = FirestoreDbHelper(application)
    private val TAG = ChatViewModel::class.java.simpleName

    fun sendMessage(msg: ChatMessage) {
        d(TAG, "sendMessage()")
        fireDb.sendMessage(msg)
    }

    fun getMessages(rxId: String): MutableLiveData<ArrayList<ChatMessage>>? {
        var result: MutableLiveData<ArrayList<ChatMessage>>? = null
        fireDb.getMessagesByRxId(rxId, object : FirestoreDbHelper.GetMessagesListener {
            override fun getMessagesSuccess(list: MutableLiveData<ArrayList<ChatMessage>>) {
                result = list
            }

            override fun getMessagesFailure() {
                result = null
            }
        })
        return result
    }

    fun getAllUsers(): MutableLiveData<ArrayList<FUser>>? {
        var result: MutableLiveData<ArrayList<FUser>>? = null
        fireDb.getAllUsers(object: FirestoreDbHelper.GetUsersListener {
            override fun getUsersSuccess(userList: MutableLiveData<ArrayList<FUser>>) {
                result = userList
            }

            override fun getUsersFailure() {
                result = null
            }

        })
        return result
    }

}