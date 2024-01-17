package com.sachin.nutrify.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sachin.nutrify.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setNavGraph(intent.extras)
    }

    private fun setNavGraph(args: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph_auth)
        navGraph.setStartDestination(R.id.signInFragment)
        navController.setGraph(navGraph, args)
    }

    companion object {
        const val RESULT_ALREADY_LOGGED_IN = -1

        const val ACTION_IS_LOGGED_OUT = "logged_out"
    }
}
