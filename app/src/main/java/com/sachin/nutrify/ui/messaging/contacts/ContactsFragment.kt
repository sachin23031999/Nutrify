package com.sachin.nutrify.ui.messaging.contacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sachin.nutrify.R
import com.sachin.nutrify.databinding.FragmentContactsBinding
import com.sachin.nutrify.extension.handleOnBackPressed
import com.sachin.nutrify.extension.navigate
import com.sachin.nutrify.extension.setResultAndFinishActivity
import com.sachin.nutrify.extension.showToast
import com.sachin.nutrify.model.User
import com.sachin.nutrify.ui.auth.AuthActivity
import com.sachin.nutrify.ui.auth.AuthActivity.Companion.ACTION_IS_LOGGED_OUT
import com.sachin.nutrify.ui.messaging.MessagingActivity.Companion.KEY_USER_INFO
import com.sachin.nutrify.utils.Logger
import org.koin.android.ext.android.inject
import timber.log.Timber
import timber.log.Timber.d

class ContactsFragment : Fragment(), UserInfoAdapterListener {

    private val binding by lazy { FragmentContactsBinding.inflate(layoutInflater) }

    private val viewModel: ContactsViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d("onViewCreated()")
        binding.contactsToolbar.inflateMenu(R.menu.contacts_menu)
        setupBackPress()
        handleToolbarClick()
        setupObservers()
        viewModel.fetchContacts()
    }

    private fun handleToolbarClick() {
        binding.contactsToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_logout -> {
                    logout()
                }

                R.id.action_settings -> {
                    Timber.d("Settings clicked")
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setupRecyclerView(list: List<User>) {
        val recyclerView = binding.rvContacts
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ContactsAdapter()
        adapter.mUserInfoAdapterListener = this
        adapter.updateUsers(list)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ContactsState.GetContactsSuccess -> {
                    setupRecyclerView(state.list)
                }

                is ContactsState.GetContactsFailed -> {
                    showToast("Error: ${state.error}")
                }
            }
        }
    }

    private fun logout() {
        viewModel.logout()
        startActivity(
            Intent(requireContext(), AuthActivity::class.java)
                .apply {
                    putExtra(ACTION_IS_LOGGED_OUT, true)
                }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onLayoutClicked(userInfo: User) {
        navigate(
            R.id.action_contactsFragment_to_chatMessageFragment,
            bundleOf(Pair(KEY_USER_INFO, userInfo))
        )
    }

    private fun setupBackPress() {
        handleOnBackPressed {
            setResultAndFinishActivity(AuthActivity.RESULT_ALREADY_LOGGED_IN)
        }
    }
}
