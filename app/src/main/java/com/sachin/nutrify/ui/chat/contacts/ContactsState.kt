package com.sachin.nutrify.ui.chat.contacts

import com.sachin.nutrify.model.User

sealed class ContactsState {

    data class GetContactsSuccess(val list: List<User>) : ContactsState()
    data class GetContactsFailed(val error: String) : ContactsState()
}
