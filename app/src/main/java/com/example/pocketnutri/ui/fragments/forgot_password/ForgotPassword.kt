package com.example.pocketnutri.ui.fragments.forgot_password

import android.os.Bundle
import android.view.View
import com.example.pocketnutri.R
import com.example.pocketnutri.databinding.FragmentForgotPasswordBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import com.example.pocketnutri.ui.util.validation.isEmailValid
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.pocketnutri.R.id.action_forgotPassword_to_signIn as toSignIn

class ForgotPassword : BaseFragment(R.layout.fragment_forgot_password), View.OnClickListener {

    private val binding: FragmentForgotPasswordBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.forgotPasswordConfirmButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.forgotPasswordConfirmButton -> passwordReset()
        }
    }

    private fun passwordReset() {
        val email = binding.forgotPasswordEmailEditText.text.toString()
        if (email.isEmpty()) {
            toast(R.string.forgot_password_empty_email_message)
        } else if (!email.isEmailValid()) {
            toast(R.string.email_incorrect_message)
        } else {
            Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigate(toSignIn)
                    toast(R.string.forgot_password_success_sent_message)
                } else {
                    toast(R.string.unregistered_email_message)
                }
            }
        }
    }
}