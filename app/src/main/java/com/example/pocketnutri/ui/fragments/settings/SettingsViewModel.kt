package com.example.pocketnutri.ui.fragments.settings

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.data.constants.EMAIL
import com.example.pocketnutri.data.constants.REMEMBER_USER
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: PrefsUseCase
):ViewModel() {
    fun signOut() = useCase.setPrefBoolean(REMEMBER_USER, false)
    fun getPrefString() = useCase.getPrefString(EMAIL)
}