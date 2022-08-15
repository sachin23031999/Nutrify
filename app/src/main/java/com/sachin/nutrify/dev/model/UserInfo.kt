package com.sachin.nutrify.dev.model

data class UserInfo(
    var name: String,
    var desc: String,
    var imageUrl: String,
    var experience: String,
    var isVerified: Boolean,
    var price: String,
    var ratings: Int,
    var totalChatDone: Int
)
