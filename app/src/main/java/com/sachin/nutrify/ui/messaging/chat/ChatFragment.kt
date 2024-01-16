package com.sachin.nutrify.ui.messaging.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl.Companion.COLLECTION_CALLS
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl.Companion.KEY_TYPE
import com.sachin.nutrify.databinding.FragmentChatBinding
import com.sachin.nutrify.extension.handleOnBackPressed
import com.sachin.nutrify.extension.showToast
import com.sachin.nutrify.model.User
import com.sachin.nutrify.ui.messaging.MessagingActivity.Companion.KEY_USER_INFO
import com.sachin.nutrify.utils.CallType
import com.sachin.nutrify.utils.Constants
import com.sachin.nutrify.utils.SharedPrefHelper
import com.sachin.nutrify.utils.Utils
import com.sachin.nutrify.webrtc.RTCActivity
import org.koin.android.ext.android.inject
import timber.log.Timber
import timber.log.Timber.d

class ChatFragment : Fragment() {

    private var mUser: User? = null
    private val binding by lazy { FragmentChatBinding.inflate(layoutInflater) }
    private val viewModel: ChatViewModel by inject()
    private val pref = SharedPrefHelper.getInstance(context)
    private val fireDb = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mUser = it.getParcelable(KEY_USER_INFO, User::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackPress()
        binding.tvUserName.text = mUser?.firstName + " " + mUser?.lastName
        setupObservers()
        setupButtons()
        mUser?.let { viewModel.getMessages(it.id) }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ChatState.GetMessageSuccess -> {
                    setupRecyclerView(state.list)
                }
                is ChatState.GetMessageFailed -> {
                    showToast("Error: ${state.error}")
                }
            }
        }
    }

    private fun setupRecyclerView(list: List<ChatMessage>) {
        val recyclerView = binding.rvChatStack
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ChatAdapter(mUser?.id!!)
        adapter.updateMessages(list)
        recyclerView.adapter = adapter
        // adapter.updateMessages(Utils.generateDummyMessages(10))
        if (list.isNotEmpty()) recyclerView.smoothScrollToPosition(list.size - 1)
    }

    private fun setupButtons() {
        binding.fabSendMessage.setOnClickListener {
            val msg = ChatMessage(
                pref.getString(Constants.SIGNED_IN_USER_UID)!!,
                mUser?.id!!,
                binding.messageBox.text.toString(),
                Utils.currentTmsInMillis(),
            )
            binding.messageBox.setText("")
            viewModel.sendMessage(msg)
        }

        binding.ivVideoCall.setOnClickListener {
            d("Video call icon clicked")
            Constants.isInitiatedNow = true
            Constants.isCallEnded = true
            initVideoCall(mUser!!.id)
        }
    }

    private fun initVideoCall(remoteId: String) {
        Timber.d("initVideoCall()")
        fireDb.collection(COLLECTION_CALLS)
            .document(remoteId)
            .get()
            .addOnSuccessListener {
                d("initVideoCall success, $it")
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

    private fun setupBackPress() {
        handleOnBackPressed {
            findNavController().popBackStack()
        }
    }
}
