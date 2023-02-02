package com.example.pocketnutri.ui.fragments.ingredients_details

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.data.constants.CALORIES
import com.example.pocketnutri.domain.usecase.ApiUseCase
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IngredientDetailsViewModel @Inject constructor(
    private val apiUseCase: ApiUseCase,
    private val prefsUseCase: PrefsUseCase
) : ViewModel() {

    suspend fun getIngredientDetails(id: String) = apiUseCase.getIngredientInfo(id)

    fun getPrefsString() = prefsUseCase.getPrefString(CALORIES)
    fun setPrefsString(value:String) = prefsUseCase.setPrefString(CALORIES, value)
}