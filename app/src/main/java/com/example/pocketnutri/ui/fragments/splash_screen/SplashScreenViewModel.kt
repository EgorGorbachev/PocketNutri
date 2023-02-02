package com.example.pocketnutri.ui.fragments.splash_screen

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.data.constants.CALORIES
import com.example.pocketnutri.data.constants.REMEMBER_USER
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val prefsUseCase: PrefsUseCase
):ViewModel() {
    fun getPrefsBoolean() = prefsUseCase.getPrefBoolean(REMEMBER_USER)

    fun deletePrefs() =
        prefsUseCase.deletePref(CALORIES)
}