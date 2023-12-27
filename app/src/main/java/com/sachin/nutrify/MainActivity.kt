package com.sachin.nutrify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sachin.nutrify.ui.call.IncomingCallFragment
import com.sachin.nutrify.ui.chat.ChatActivity
import com.sachin.nutrify.utils.CallType
import com.sachin.nutrify.utils.Constants
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (viewModel.isSignedIn()) {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        } else {
            setNavGraph(intent.extras)
        }

        /*db.collection(Constants.Database.KEY_CALLS)
            .document(sharedPref.getString(Constants.SIGNED_IN_USER_UID)!!)
            .addSnapshotListener { payload, error ->
                val type = payload!!.getString(Constants.Database.KEY_TYPE)
                when (type) {
                    CallType.OFFER -> {
                        startActivity(Intent(this, IncomingCallFragment::class.java))
                    }
                    CallType.END_CALL -> {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }*/
    }

    private fun setNavGraph(args: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph_auth)
        navGraph.setStartDestination(R.id.signInFragment)
        navController.setGraph(navGraph, args)
    }
}
