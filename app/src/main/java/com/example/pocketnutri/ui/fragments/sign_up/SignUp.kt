package com.example.pocketnutri.ui.fragments.sign_up

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.pocketnutri.R
import com.example.pocketnutri.data.constants.REMEMBER_USER
import com.example.pocketnutri.databinding.FragmentSignUpBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import com.example.pocketnutri.ui.util.validation.isEmailValid
import com.example.pocketnutri.ui.util.validation.isPasswordValid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import com.example.pocketnutri.R.id.action_signUp_to_signIn as signUpToSignIn
import com.example.pocketnutri.R.id.action_signUp_to_calculator as toCalculator

@AndroidEntryPoint
class SignUp : BaseFragment(R.layout.fragment_sign_up), View.OnClickListener {

    private val binding: FragmentSignUpBinding by viewBinding()
    private val viewModel: SingUpViewModel by viewModels()
    private lateinit var database:DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.toSignInContainerButton.setOnClickListener(this)
        binding.signUpButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.toSignInContainerButton -> navigate(signUpToSignIn)
            R.id.signUpButton -> {
                signUp(
                    binding.signUpEmailEditText.text.toString(),
                    binding.signUpPasswordEditText.text.toString(),
                    binding.signUpPasswordConfirmEditText.text.toString()
                )
            }
        }
    }

    private fun signUp(email: String, password: String, confirmPassword: String) {
        when {
            !email.isEmailValid() || email.isEmpty() -> toast(R.string.email_incorrect_message)
            !password.isPasswordValid() || email.isEmpty() -> toast(R.string.password_incorrect_message)
            confirmPassword != password -> toast(R.string.passwords_do_not_match_message)
            else -> createNewUser(email, password)
        }
    }

    private fun createNewUser(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    toast(R.string.success_sign_up_message)
                    viewModel.setPrefString(email)
                    createUserInDatabase()
                    navigate(toCalculator)
                } else {
                    simpleToast(requireContext(), task.exception.toString())
                }
            }

        viewModel.setPrefBoolean(REMEMBER_USER, binding.signUpCheckBox.isChecked)
    }

    private fun createUserInDatabase() {
        database = FirebaseDatabase.getInstance().getReference("Users")
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        database.child(currentFirebaseUser!!.uid).setValue(currentFirebaseUser.email)
    }
}