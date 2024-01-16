package com.sachin.nutrify.ui.messaging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sachin.nutrify.R

class MessagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setNavGraph(intent.extras)
    }

    private fun setNavGraph(args: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph_messaging)
        navGraph.setStartDestination(R.id.contactsFragment)
        navController.setGraph(navGraph, args)
    }

    companion object {
        const val KEY_USER_INFO = "user_info"
    }
}
