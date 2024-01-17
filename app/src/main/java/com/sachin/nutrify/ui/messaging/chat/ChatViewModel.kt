package com.sachin.nutrify.ui.messaging.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin.nutrify.data.FirestoreRepository
import com.sachin.nutrify.data.AuthRepository
import com.sachin.nutrify.extension.toLiveData
import com.sachin.nutrify.model.FUser
import kotlinx.coroutines.launch
import timber.log.Timber.d

class ChatViewModel(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<ChatState>()

    val uiState = _uiState.toLiveData()
    fun sendMessage(msg: ChatMessage) {
        d("sendMessage()")
        firestoreRepository.addMessage(msg)
    }

    fun getMessages(remoteId: String) {
        viewModelScope.launch {
            val messages = mutableListOf<ChatMessage>()
            getCurrentUserId()?.let { currentUserId ->
                messages.addAll(firestoreRepository.getSentMessages(remoteId, currentUserId))
                messages.addAll(firestoreRepository.getReceivedMessages(currentUserId, remoteId))
                _uiState.postValue(ChatState.GetMessageSuccess(messages))
            } ?: _uiState.postValue(ChatState.GetMessageFailed("current user id null"))
        }
    }

    private fun getCurrentUserId(): String? = authRepository.getAccountDetails()?.id
    fun getAllUsers(): MutableLiveData<ArrayList<FUser>>? {
        val result: MutableLiveData<ArrayList<FUser>>? = null
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
