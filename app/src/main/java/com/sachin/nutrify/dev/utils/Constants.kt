package com.sachin.nutrify.dev.utils

class Constants {

    companion object {
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val ENC_IMAGE = "encoded_image"
        const val IS_SIGNED_IN = "signed_in"
        const val SIGNED_IN_USER_UID = "signed_in_user_uid"

        const val KEY_SENDER_ID = "sender_id"
        const val KEY_RECEIVER_ID = "receiver_id"
        const val KEY_MESSAGE = "message"
        const val KEY_TIMESTAMP = "tms"
        const val KEY_COLLECTION_CHAT = "chat"
    }
    interface Database {
        companion object {
            const val USERS = "users"
            const val CHATS = "chat"
        }
    }
}