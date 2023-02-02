package com.example.pocketnutri.ui.fragments.recipes_details

import androidx.lifecycle.ViewModel
import com.example.pocketnutri.domain.usecase.ApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val apiUseCase: ApiUseCase
):ViewModel() {
    suspend fun getRecipeDetails(id:String) = apiUseCase.getRecipeInfo(id)
}