package com.example.pocketnutri.ui.fragments.sign_in

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.data.constants.EMAIL
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val prefsUseCase: PrefsUseCase
):ViewModel() {
    fun setPrefBoolean(prefName: String, value: Boolean) = prefsUseCase.setPrefBoolean(prefName, value)
    fun setPrefString(value: String) = prefsUseCase.setPrefString(EMAIL,value)
}