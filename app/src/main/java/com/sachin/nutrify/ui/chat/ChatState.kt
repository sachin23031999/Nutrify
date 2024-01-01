package com.sachin.nutrify.ui.chat

import com.sachin.nutrify.model.UserInfo

sealed class ChatState {
    data class GetMessageSuccess(val list: List<ChatMessage>) : ChatState()
    data class GetMessageFailed(val error: String) : ChatState()
}
