package com.sachin.nutrify.dev

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.nutrify.dev.utils.Constants
import com.sachin.nutrify.dev.utils.Constants.Companion.IS_SIGNED_IN
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.SharedPrefHelper
import com.sachin.nutrify.dev.utils.Utils


class MainActivity : AppCompatActivity() {
   // private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPref: SharedPrefHelper
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d(TAG, "OnCreate()")
        setContentView(R.layout.activity_splash)
        sharedPref = SharedPrefHelper.getInstance(this)
     //   binding = ActivitySignInBinding.inflate(layoutInflater)
        if(sharedPref.getBoolean(Constants.IS_SIGNED_IN)) {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
        else {
            Utils.navigateTo(this,
                R.id.fragment_container,
                SignInFragment.newInstance("", ""),
                ""
            )
            //show SIGNUP INSTEAD?
            // then transact to SignupFragment
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }

    }


}

