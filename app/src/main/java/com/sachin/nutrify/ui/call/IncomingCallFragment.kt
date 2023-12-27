package com.sachin.nutrify.ui.call

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sachin.nutrify.R
import com.sachin.nutrify.databinding.FragmentIncomingCallBinding
import com.sachin.nutrify.utils.Constants
import com.sachin.nutrify.utils.Logger
import com.sachin.nutrify.utils.SharedPrefHelper
import com.sachin.nutrify.webrtc.RTCActivity

class IncomingCallFragment : AppCompatActivity() {
    private val TAG = IncomingCallFragment::class.java.simpleName
    private val binding by lazy { FragmentIncomingCallBinding.inflate(layoutInflater) }
    private lateinit var sharedPref: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(TAG, "onCreate()")
        setContentView(R.layout.fragment_incoming_call)

        sharedPref = SharedPrefHelper.getInstance(this)

        binding.ivAcceptCall.setOnClickListener {
            Logger.d(TAG, "Accept call clicked")
            acceptCall()
        }
        binding.ivRejectCall.setOnClickListener {
            Logger.d(TAG, "Reject call clicked")
            rejectCall()
        }
    }

    private fun acceptCall() {
        val intent = Intent(this, RTCActivity::class.java)
        intent.putExtra("meetingID", sharedPref.getString(Constants.SIGNED_IN_USER_UID)!!)
        intent.putExtra("isJoin", true)
        startActivity(intent)
    }

    private fun rejectCall() {
    }
}
