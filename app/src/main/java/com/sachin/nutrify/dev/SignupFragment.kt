package com.sachin.nutrify.dev

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sachin.nutrify.dev.databinding.FragmentSignupBinding
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.model.User
import com.sachin.nutrify.dev.model.UserInfo
import kotlinx.android.synthetic.main.fragment_signup.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignupFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        binding = FragmentSignupBinding.inflate(layoutInflater)
        ivProfile.setOnClickListener {

        }

        buttonSignup.setOnClickListener {
            val userData = User(
                tvFirstName.text.toString(),
                tvLastName.text.toString(),
                tvEmail.text.toString(),
                tvPassword.text.toString(),
            )
            viewModel.signUp(userData, object : FirestoreDbHelper.SignUpListener {
                override fun signUpSuccess() {
                    val intent = Intent(requireActivity(), ChatActivity::class.java)
                    startActivity(intent)
                }

                override fun signUpFailure() {
                    Toast.makeText(
                        requireActivity(),
                        "Signup failed, please try again",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            })
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}