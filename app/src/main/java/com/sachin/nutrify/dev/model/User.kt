package com.sachin.nutrify.dev.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var encodedImage: String? = null
)

@Entity
@Parcelize
data class FUser(
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var encodedImage: String? = null
): Parcelable