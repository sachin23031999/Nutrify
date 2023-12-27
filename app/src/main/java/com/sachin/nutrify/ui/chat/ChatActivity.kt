package com.sachin.nutrify.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sachin.nutrify.R
import com.sachin.nutrify.utils.Logger
import com.sachin.nutrify.utils.Utils

class ChatActivity : AppCompatActivity() {
    private val TAG = ChatActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        Logger.d(TAG, "onCreate()")
        Utils.navigateTo(
            this,
            R.id.fragment_container_chat,
            ChatUserFragment.newInstance("", ""),
            "",
        )
    }
}
