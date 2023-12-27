package com.sachin.nutrify.utils

class Constants {

    companion object {
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val ENC_IMAGE = "encoded_image"
        const val IS_SIGNED_IN = "signed_in"
        const val SIGNED_IN_USER_UID = "signed_in_user_uid"

        var isCallEnded: Boolean = false
        var isInitiatedNow: Boolean = true
    }
}

object CallType {
    const val OFFER = "OFFER"
    const val ANSWER = "ANSWER"
    const val END_CALL = "END_CALL"
}
