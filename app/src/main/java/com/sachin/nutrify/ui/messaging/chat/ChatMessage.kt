package com.sachin.nutrify.ui.messaging.chat

data class ChatMessage(
    val senderId: String,
    val receiverId: String,
    val message: String,
    val tms: String
)