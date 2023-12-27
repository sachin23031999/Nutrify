package com.sachin.nutrify.model

import androidx.room.Entity

@Entity
data class User(
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var encodedImage: String? = null
)

@Entity
data class FUser(
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var encodedImage: String? = null
)
