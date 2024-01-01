package com.sachin.nutrify.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

@Entity
data class User(
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var imageUrl: String? = null,
    val federatedToken: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(imageUrl)
        parcel.writeString(federatedToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity
data class FUser(
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var encodedImage: String? = null
)
