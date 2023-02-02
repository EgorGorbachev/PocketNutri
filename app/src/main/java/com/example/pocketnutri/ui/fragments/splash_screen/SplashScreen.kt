package com.example.pocketnutri.ui.fragments.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pocketnutri.R
import com.example.pocketnutri.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.pocketnutri.R.id.action_splashScreen_to_calculator as toCalculator
import com.example.pocketnutri.R.id.action_splashScreen_to_signIn as toSignIn

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : BaseFragment(R.layout.fragment_splash_screen) {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.deletePrefs()

        CoroutineScope(lifecycleScope.coroutineContext).launch {
            delay(2000)
            if (viewModel.getPrefsBoolean()) navigate(toCalculator)
            else navigate(toSignIn)
        }
    }
}