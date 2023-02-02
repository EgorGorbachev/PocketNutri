package com.example.pocketnutri.ui.fragments.recipes

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.data.constants.CALORIES
import com.example.pocketnutri.domain.usecase.ApiUseCase
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val apiUseCase: ApiUseCase
):ViewModel(){
    suspend fun getRecipes(query:String) = apiUseCase.getRecipes(query)
}