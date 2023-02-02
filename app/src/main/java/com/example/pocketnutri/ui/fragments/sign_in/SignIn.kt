package com.example.pocketnutri.ui.fragments.sign_in


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.example.pocketnutri.R
import com.example.pocketnutri.data.constants.REMEMBER_USER
import com.example.pocketnutri.databinding.FragmentSignInBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import com.example.pocketnutri.R.id.action_signIn_to_calculator as toCalculator
import com.example.pocketnutri.R.id.action_signIn_to_forgotPassword as signInToForgotPassword
import com.example.pocketnutri.R.id.action_signIn_to_signUp as signInToSignUp

@AndroidEntryPoint
class SignIn : BaseFragment(R.layout.fragment_sign_in), View.OnClickListener {

    private val binding: FragmentSignInBinding by viewBinding()
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        onBackPressedClose()
    }

    private fun initListeners() {
        binding.toSignUpContainerButton.setOnClickListener(this)
        binding.signInToForgotPasswordButton.setOnClickListener(this)
        binding.signInButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.toSignUpContainerButton -> navigate(signInToSignUp)
            R.id.signInToForgotPasswordButton -> navigate(signInToForgotPassword)
            R.id.signInButton -> checkEnteredData(
                binding.signInEmailEditText.text.toString(),
                binding.signInPasswordEditText.text.toString()
            )
        }
    }

    private fun checkEnteredData(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) signIn(email, password)
        else toast(R.string.email_or_password_field_empty_message)
    }

    private fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast(R.string.success_sign_up_message)
                    viewModel.setPrefString(email)
                    navigate(toCalculator)
                } else toast(R.string.unregistered_email_or_incorrect_password_message)
            }
        viewModel.setPrefBoolean(REMEMBER_USER, binding.signInCheckBox.isChecked)
    }


    private fun onBackPressedClose() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }
}