package com.sachin.nutrify.dev

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.nutrify.dev.adapter.MessageAdapter
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.model.ChatMessage
import com.sachin.nutrify.dev.model.FUser
import com.sachin.nutrify.dev.utils.Constants
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.SharedPrefHelper
import com.sachin.nutrify.dev.utils.Utils
import com.sachin.nutrify.dev.webrtc.RTCActivity
import kotlinx.android.synthetic.main.fragment_chat_message.*

private const val USER_INFO = "userInfo"

class ChatMessageFragment : Fragment() {

    private val TAG = ChatMessageFragment::class.java.simpleName
    private var mUser: FUser? = null
    private val chatViewModel: ChatViewModel by viewModels()
    private val pref = SharedPrefHelper.getInstance(context)
    private val fireDb = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mUser = it.getParcelable(USER_INFO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvUserName.text = mUser?.firstName + " " + mUser?.lastName

        val recyclerView = rvChatStack
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = MessageAdapter(mUser?.id!!)
        recyclerView.adapter = adapter
       // adapter.addMessage(Utils.generateDummyMessages(10))

        fabSendMessage.setOnClickListener {
            val msg = ChatMessage(
                pref.getString(Constants.SIGNED_IN_USER_UID)!!,
                mUser?.id!!,
                messageBox.text.toString(),
                Utils.currentTmsInMillis()
            )
            messageBox.setText("")
            chatViewModel.sendMessage(msg)
        }

        chatViewModel.getMessages(mUser?.id!!)?.observe(this) { list ->
            d(TAG, "getMessages()")
            list.forEach {
                d(TAG, "msg -> $it")
            }
            adapter.addMessage(list)
            if (list.isNotEmpty()) recyclerView.smoothScrollToPosition(list.size - 1)
        }

        ivVideoCall.setOnClickListener {
            d(TAG,"Video call icon clicked")
            Constants.isInitiatedNow = true
            Constants.isCallEnded = true
            initVideoCall(mUser!!.id)
        }


    }

    private fun initVideoCall(remoteId: String) {
        d(TAG, "initVideoCall()")
        fireDb.collection("calls")
            .document(remoteId)
            .get()
            .addOnSuccessListener {
                d(TAG, "initVideoCall(), addOnSuccessListener(), $it")
                if (it["type"]=="OFFER" || it["type"]=="ANSWER" /*|| it["type"]=="END_CALL"*/) {
                    //meeting_id.error = "Please enter new meeting ID"
                } else if (!it.exists() || it["type"] == "END_CALL"){
                    val intent = Intent(requireActivity(), RTCActivity::class.java)
                    intent.putExtra("meetingID",remoteId)
                    intent.putExtra("isJoin",false)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                d(TAG, "Some error occurred, " + it.message)
                //remoteId.error = "Please enter new meeting ID"
            }
    }

    companion object {

        @JvmStatic
        fun newInstance(userInfo: FUser) =
            ChatMessageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_INFO, userInfo)
                }
            }
    }
}