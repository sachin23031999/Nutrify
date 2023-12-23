package com.sachin.nutrify.dev

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sachin.nutrify.dev.adapter.UserInfoAdapter
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.model.FUser
import com.sachin.nutrify.dev.utils.Constants
import com.sachin.nutrify.dev.utils.Logger
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.SharedPrefHelper
import com.sachin.nutrify.dev.utils.Utils
import com.sachin.nutrify.dev.webrtc.RTCActivity
import kotlinx.android.synthetic.main.fragment_chat_user.*
import kotlinx.android.synthetic.main.fragment_incoming_call.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class IncomingCallFragment: AppCompatActivity() {
    private val TAG = IncomingCallFragment::class.java.simpleName
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sharedPref: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d(TAG, "onCreate()")
        setContentView(R.layout.fragment_incoming_call )
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
        sharedPref = SharedPrefHelper.getInstance(this)

        ivAcceptCall.setOnClickListener {
            d(TAG, "Accept call clicked")
            acceptCall()
        }
        ivRejectCall.setOnClickListener {
            d(TAG, "Reject call clicked")
            rejectCall()
        }

    }


    private fun acceptCall() {
        val intent = Intent(this, RTCActivity::class.java)
        intent.putExtra("meetingID", sharedPref.getString(Constants.SIGNED_IN_USER_UID)!!)
        intent.putExtra("isJoin",true)
        startActivity(intent)
    }

    private fun rejectCall() {

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}