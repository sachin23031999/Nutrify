package com.sachin.nutrify.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.sachin.nutrify.R
import com.sachin.nutrify.databinding.FragmentSignInBinding
import com.sachin.nutrify.extension.finishActivity
import com.sachin.nutrify.extension.handleOnBackPressed
import com.sachin.nutrify.extension.navigate
import com.sachin.nutrify.extension.onBackPressed
import com.sachin.nutrify.extension.showToast
import com.sachin.nutrify.model.User
import com.sachin.nutrify.ui.messaging.MessagingActivity
import org.koin.android.ext.android.inject
import timber.log.Timber.d

class SignInFragment : Fragment() {
    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }
    private val viewModel: AuthViewModel by inject()

    private val googleSignInContract = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            showToast("Login success")
            result.data?.let { handleGoogleSignIn(it) }
        } else {
            showToast("Login failed, try again")
        }
    }

    private val contractMessaging = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AuthActivity.RESULT_ALREADY_LOGGED_IN) {
            onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackPress()
        checkLogin()
        setupObservers()
        viewModel.init(requireActivity())
    }

    private fun checkLogin() {
        if (viewModel.isUserLoggedIn(requireContext())) {
            d("User already logged in")
            startActivity(Intent(requireContext(), MessagingActivity::class.java))
        }
    }

    private fun setupBackPress() {
        handleOnBackPressed {
            finishActivity()
        }
    }

    private fun setupObservers() {
        viewModel.authUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthState.InitSuccess -> {
                    setupButtons()
                }

                is AuthState.InitFailure -> {
                }

                else -> {}
            }
        }
    }

    private fun handleGoogleSignIn(data: Intent) {
        viewModel.getLoginDetails(data)?.let {
            d("user details: ${it.email}")
            handleSignIn(it)
        }
    }

    private fun handleSignIn(user: User) {
        viewModel.updateAccount(user)
        contractMessaging.launch(
            Intent(requireActivity(), MessagingActivity::class.java)
        )
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
        binding.btGoogleLogin.setOnClickListener {
            initiateGoogleSignIn()
        }
    }

    private fun initiateGoogleSignIn() {
        googleSignInContract.launch(viewModel.signInIntent())
    }

    private fun isValidDetails(id: String, password: String): Boolean {
        return true
    }
}
