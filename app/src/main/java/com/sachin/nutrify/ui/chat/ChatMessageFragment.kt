package com.sachin.nutrify.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.nutrify.R
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl.Companion.COLLECTION_CALLS
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl.Companion.KEY_TYPE
import com.sachin.nutrify.databinding.FragmentChatMessageBinding
import com.sachin.nutrify.model.FUser
import com.sachin.nutrify.ui.adapter.MessageAdapter
import com.sachin.nutrify.utils.CallType
import com.sachin.nutrify.utils.Constants
import com.sachin.nutrify.utils.Logger
import com.sachin.nutrify.utils.SharedPrefHelper
import com.sachin.nutrify.utils.Utils
import com.sachin.nutrify.webrtc.RTCActivity
import timber.log.Timber

class ChatMessageFragment : Fragment() {

    private val TAG = ChatMessageFragment::class.java.simpleName
    private var mUser: FUser? = null
    private val binding by lazy { FragmentChatMessageBinding.inflate(layoutInflater) }
    private val chatViewModel: ChatViewModel by viewModels()
    private val pref = SharedPrefHelper.getInstance(context)
    private val fireDb = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          //  mUser = it.getParcelable(USER_INFO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvUserName.text = mUser?.firstName + " " + mUser?.lastName

        val recyclerView = binding.rvChatStack
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = MessageAdapter(mUser?.id!!)
        recyclerView.adapter = adapter
        // adapter.addMessage(Utils.generateDummyMessages(10))

        binding.fabSendMessage.setOnClickListener {
            val msg = ChatMessage(
                pref.getString(Constants.SIGNED_IN_USER_UID)!!,
                mUser?.id!!,
                binding.messageBox.text.toString(),
                Utils.currentTmsInMillis(),
            )
            binding.messageBox.setText("")
            chatViewModel.sendMessage(msg)
        }

        chatViewModel.getMessages(mUser?.id!!)?.observe(viewLifecycleOwner) { list ->
            Logger.d(TAG, "getMessages()")
            list.forEach {
                Logger.d(TAG, "msg -> $it")
            }
            adapter.addMessage(list)
            if (list.isNotEmpty()) recyclerView.smoothScrollToPosition(list.size - 1)
        }

        binding.ivVideoCall.setOnClickListener {
            Logger.d(TAG, "Video call icon clicked")
            Constants.isInitiatedNow = true
            Constants.isCallEnded = true
            initVideoCall(mUser!!.id)
        }
    }

    private fun initVideoCall(remoteId: String) {
        Timber.d( "initVideoCall()")
        fireDb.collection(COLLECTION_CALLS)
            .document(remoteId)
            .get()
            .addOnSuccessListener {
                Timber.d( "initVideoCall success, $it")
                if (it[KEY_TYPE] == CallType.OFFER ||
                    it[KEY_TYPE] == CallType.ANSWER
                    /*|| it["type"]=="END_CALL"*/
                ) {
                    // meeting_id.error = "Please enter new meeting ID"
                    // the state of user is END_CALL
                } else if (!it.exists() || it[KEY_TYPE] == CallType.END_CALL) {
                    val intent = Intent(requireActivity(), RTCActivity::class.java)
                    intent.putExtra("meetingID", remoteId)
                    intent.putExtra("isJoin", false)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Timber.d("Some error occurred, ${it.message}")
                // remoteId.error = "Please enter new meeting ID"
            }
    }

    companion object {

        @JvmStatic
        fun newInstance(userInfo: FUser) =
            ChatMessageFragment().apply {
                arguments = Bundle().apply {
                 //   putParcelable(USER_INFO, userInfo)
                }
            }
    }
}
