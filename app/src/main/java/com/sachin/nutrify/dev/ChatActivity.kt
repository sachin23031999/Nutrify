package com.sachin.nutrify.dev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sachin.nutrify.dev.utils.Utils

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //Utils.navigateTo(this, ChatUserFragment.newInstance("", ""), "")
    }
}