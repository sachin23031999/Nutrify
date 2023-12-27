package com.sachin.nutrify.ui.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl
import com.sachin.nutrify.model.FUser
import com.sachin.nutrify.utils.Logger

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val fireDb = FirestoreRepositoryImpl()
    private val TAG = ChatViewModel::class.java.simpleName

    fun sendMessage(msg: ChatMessage) {
        Logger.d(TAG, "sendMessage()")
        fireDb.addMessage(msg)
    }

    fun getMessages(rxId: String): MutableLiveData<ArrayList<ChatMessage>>? {
        var result: MutableLiveData<ArrayList<ChatMessage>>? = null
        /*fireDb.getMessages(
            rxId,
            object : FirestoreRepositoryImpl.GetMessagesListener {
                override fun getMessagesSuccess(list: MutableLiveData<ArrayList<ChatMessage>>) {
                    result = list
                }

                override fun getMessagesFailure() {
                    result = null
                }
            },
        )*/
        return result
    }

    fun getAllUsers(): MutableLiveData<ArrayList<FUser>>? {
        var result: MutableLiveData<ArrayList<FUser>>? = null
        /*fireDb.getAllUsers(object : FirestoreRepositoryImpl.GetUsersListener {
            override fun getUsersSuccess(userList: MutableLiveData<ArrayList<FUser>>) {
                result = userList
            }

            override fun getUsersFailure() {
                result = null
            }
        })*/
        return result
    }
}
