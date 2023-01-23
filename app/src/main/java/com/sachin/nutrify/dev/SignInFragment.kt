package com.sachin.nutrify.dev

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textview.MaterialTextView
import com.sachin.nutrify.dev.databinding.FragmentSignInBinding
import com.sachin.nutrify.dev.firebase.FirestoreDbHelper
import com.sachin.nutrify.dev.utils.Constants
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.SharedPrefHelper
import com.sachin.nutrify.dev.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SignInFragment : Fragment() {
    private val TAG = SignInFragment::class.java.simpleName
    private val viewModel: SharedViewModel by viewModels()
//    private val sharedPref = SharedPrefHelper.getInstance(requireActivity())

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSignInBinding

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
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d(TAG, "onViewCreated()")

        binding = FragmentSignInBinding.inflate(layoutInflater)


        view.tvSignUp.setOnClickListener {
            d(TAG, "signup clicked")

            Utils.navigateTo(
                requireActivity(),
                R.id.fragment_container,
                SignupFragment.newInstance("",""),
                ""
            )
        }
        view.btLogin.setOnClickListener {
            d(TAG, "login clicked")
            val id = tvEmailId.text.toString()
            val password = tvPassword.text.toString()
            if (isValidDetails(id, password)) {
                var temp = false
                viewModel.signIn(id, password, object : FirestoreDbHelper.SignInListener {
                    override fun signInSuccess() {
                        d(TAG, "signInSuccess()")
                        //launch chat activity
                        //if(!temp) {
                            temp = true

                            val intent = Intent(requireActivity(), ChatActivity::class.java)
                            startActivity(intent)
                        //}
                    }

                    override fun signInFailure() {
                        d(TAG, "signInFailure()")
                        Toast.makeText(requireActivity(), "User not found", Toast.LENGTH_SHORT)
                            .show()
                    }

                })

            }
        }
    }

    private fun isValidDetails(id: String, password: String): Boolean {
        return true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}