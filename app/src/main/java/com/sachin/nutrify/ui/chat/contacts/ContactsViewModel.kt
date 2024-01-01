package com.sachin.nutrify.ui.chat.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin.nutrify.data.FirestoreRepository
import com.sachin.nutrify.data.room.AuthRepository
import com.sachin.nutrify.extension.toLiveData
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<ContactsState>()

    val uiState = _uiState.toLiveData()

    fun fetchContacts() {
        viewModelScope.launch {
            firestoreRepository.getAllUsers()
                .takeIf { it.isNotEmpty() }
                ?.let { list ->
                    _uiState.postValue(ContactsState.GetContactsSuccess(list))
                }
                ?: _uiState.postValue(ContactsState.GetContactsFailed("no contacts found"))
        }
    }
}
