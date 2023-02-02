package com.example.pocketnutri.ui.fragments.calculator

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.data.constants.KEY_SHARED_PREFS_NAME
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val prefsUseCase: PrefsUseCase
):ViewModel() {
    fun getPrefsBoolean() = prefsUseCase.getPrefBoolean(KEY_SHARED_PREFS_NAME)
}