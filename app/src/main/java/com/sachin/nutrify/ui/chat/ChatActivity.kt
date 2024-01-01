package com.sachin.nutrify.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sachin.nutrify.R
import com.sachin.nutrify.ui.chat.contacts.ContactsFragment
import com.sachin.nutrify.utils.Logger
import com.sachin.nutrify.utils.Utils

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setNavGraph(intent.extras)
    }

    private fun setNavGraph(args: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph_chat)
        navGraph.setStartDestination(R.id.contactsFragment)
        navController.setGraph(navGraph, args)
    }
}
