package com.sachin.nutrify.ui.messaging.contacts

import com.sachin.nutrify.model.User

sealed class ContactsState {

    data class GetContactsSuccess(val list: List<User>) : ContactsState()
    data class GetContactsFailed(val error: String) : ContactsState()
}
