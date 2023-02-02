package com.example.pocketnutri.ui.fragments.ingredients

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.data.constants.CALORIES
import com.example.pocketnutri.domain.usecase.ApiUseCase
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private val apiUseCase: ApiUseCase,
    private val prefsUseCase: PrefsUseCase
) : ViewModel() {

    suspend fun getIngredients(query:String) =
        apiUseCase.getIngredients(query)

    suspend fun getIngredientInfo(id:String) =
        apiUseCase.getIngredientInfo(id)

    fun setPrefsString(value:String) =
        prefsUseCase.setPrefString(CALORIES, value)

    fun getPrefsString() =
        prefsUseCase.getPrefString(CALORIES)

}