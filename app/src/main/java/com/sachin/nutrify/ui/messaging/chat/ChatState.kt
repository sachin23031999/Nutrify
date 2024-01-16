package com.sachin.nutrify.ui.messaging.chat

sealed class ChatState {
    data class GetMessageSuccess(val list: List<ChatMessage>) : ChatState()
    data class GetMessageFailed(val error: String) : ChatState()
}
