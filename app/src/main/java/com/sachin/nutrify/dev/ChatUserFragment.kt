package com.sachin.nutrify.dev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sachin.nutrify.dev.adapter.UserInfoAdapter
import com.sachin.nutrify.dev.adapter.UserInfoAdapterListener
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.model.FUser
import com.sachin.nutrify.dev.model.UserInfo
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.Utils
import com.sachin.nutrify.dev.utils.Utils.Companion.generateDummyUsers
import com.sachin.nutrify.dev.utils.Utils.Companion.showToast
import kotlinx.android.synthetic.main.fragment_chat_user.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ChatUserFragment : Fragment(), UserInfoAdapterListener {

    private var param1: String? = null
    private var param2: String? = null
    private val TAG = ChatUserFragment::class.java.simpleName
    private val viewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d(TAG, "onCreate()")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
        d(TAG, "onViewCreated()")
        val recyclerView = rv_contacts
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = UserInfoAdapter()
        adapter.mUserInfoAdapterListener = this
        val dummyUsers = generateDummyUsers(10)
        viewModel.getAllUsers(object : FirestoreDbHelper.getUsersListener {
            override fun getUsersSuccess(userList: List<FUser>) {
                d(TAG, "getUserSuccess(), $userList")
                adapter.updateUsers(userList as MutableList<FUser>)
            }

            override fun getUsersFailure() {

            }

        })
       // adapter.updateUsers(viewModel.getAllUsers())
        recyclerView.adapter = adapter
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

    override fun onLayoutClicked(userInfo: FUser) {
        //showToast(context!!, "${userInfo.name} clicked")
        Utils.navigateTo(requireActivity(),
            R.id.fragment_container_chat,
            ChatMessageFragment.newInstance(userInfo),
            ""
        )
    }
}