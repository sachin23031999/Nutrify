package com.sachin.nutrify.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sachin.nutrify.R
import com.sachin.nutrify.ui.chat.ChatActivity
import com.sachin.nutrify.databinding.FragmentSignInBinding
import com.sachin.nutrify.extension.navigate
import org.koin.android.ext.android.inject

class SignInFragment : Fragment() {
    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }
    private val viewModel: AuthViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.init(requireActivity())
        setupButtons()
    }

    private fun setupObservers() {
        viewModel.authUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthState.InitSuccess -> {
                    setupButtons()
                }
                is AuthState.InitFailure -> {
                }
                is AuthState.LoginSuccess -> {
                    val intent = Intent(requireActivity(), ChatActivity::class.java)
                    startActivity(intent)
                }
                is AuthState.LoginFailure -> {
                    Toast.makeText(requireActivity(), "User not found", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {}
            }
        }
    }

    private fun setupButtons() {
        binding.tvSignUp.setOnClickListener {
            navigate(R.id.action_signInFragment_to_signupFragment, arguments)
        }
        binding.btLogin.setOnClickListener {
            val id = binding.tvEmailId.text.toString()
            val password = binding.tvPassword.text.toString()
            if (isValidDetails(id, password)) {
                viewModel.signIn(requireActivity(), id, password)
            }
        }
    }

    private fun isValidDetails(id: String, password: String): Boolean {
        return true
    }
}
