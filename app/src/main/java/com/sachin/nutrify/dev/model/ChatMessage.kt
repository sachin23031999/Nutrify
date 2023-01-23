package com.sachin.nutrify.dev.model

data class ChatMessage(
    val senderId: String,
    val receiverId: String,
    val message: String,
    val tms: String
)