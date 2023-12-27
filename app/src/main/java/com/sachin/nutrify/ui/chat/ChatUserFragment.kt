package com.sachin.nutrify.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sachin.nutrify.R
import com.sachin.nutrify.databinding.FragmentChatUserBinding
import com.sachin.nutrify.ui.adapter.UserInfoAdapter
import com.sachin.nutrify.ui.adapter.UserInfoAdapterListener
import com.sachin.nutrify.model.FUser
import com.sachin.nutrify.utils.Logger
import com.sachin.nutrify.utils.Utils

class ChatUserFragment : Fragment(), UserInfoAdapterListener {

    private val binding by lazy { FragmentChatUserBinding.inflate(layoutInflater) }
    private val TAG = ChatUserFragment::class.java.simpleName
    private val viewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(TAG, "onCreate()")
        arguments?.let {
         //   param1 = it.getString(ARG_PARAM1)
           // param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(TAG, "onViewCreated()")
        val recyclerView = binding.rvContacts
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = UserInfoAdapter()
        adapter.mUserInfoAdapterListener = this
        val dummyUsers = Utils.generateDummyUsers(10)
        viewModel.getAllUsers()?.observe(viewLifecycleOwner) { userList ->
            adapter.updateUsers(userList as MutableList<FUser>)
        }

       // adapter.updateUsers(viewModel.getAllUsers())
        recyclerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatUserFragment().apply {
                arguments = Bundle().apply {
                   // putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onLayoutClicked(userInfo: FUser) {
        //showToast(context!!, "${userInfo.name} clicked")
        Utils.navigateTo(
            requireActivity(),
            R.id.fragment_container_chat,
            ChatMessageFragment.newInstance(userInfo),
            ""
        )
    }
}