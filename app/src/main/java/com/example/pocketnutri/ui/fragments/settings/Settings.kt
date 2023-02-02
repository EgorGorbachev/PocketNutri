package com.example.pocketnutri.ui.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.pocketnutri.R
import com.example.pocketnutri.databinding.FragmentSettingsBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.pocketnutri.R.id.action_settings_to_signIn as toSignIn

@AndroidEntryPoint
class Settings : BaseFragment(R.layout.fragment_settings),View.OnClickListener {

    private val binding: FragmentSettingsBinding by viewBinding()
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initUi()
    }

    private fun initListeners() {
        binding.settingsSignOutButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.settingsSignOutButton -> signOut()
        }
    }

    private fun signOut() {
        viewModel.signOut()
        navigate(toSignIn)
    }

    private fun initUi() {
        binding.settingsEmail.text = viewModel.getPrefString()
    }
}