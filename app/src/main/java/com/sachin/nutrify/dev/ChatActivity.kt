package com.sachin.nutrify.dev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.Utils

class ChatActivity : AppCompatActivity() {
    private val TAG = ChatActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        d(TAG, "onCreate()")
        Utils.navigateTo(this,
            R.id.fragment_container_chat,
            ChatUserFragment.newInstance("", ""),
            "")
    }
}