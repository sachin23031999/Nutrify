package com.sachin.nutrify.dev.model

import androidx.room.Entity

@Entity
data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var encodedImage: String? = null
) {
}