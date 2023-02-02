package com.example.pocketnutri.domain.usecase

import com.example.pocketnutri.data.source.rest.models.ingredient_details.IngredientInfo
import com.example.pocketnutri.data.source.rest.models.ingredients.IngredientResponse
import com.example.pocketnutri.data.source.rest.models.recipes.RecipesResponse
import com.example.pocketnutri.data.source.rest.models.recipes_details.RecipeInfo
import com.example.pocketnutri.domain.repository.ApiRepository
import javax.inject.Inject

class ApiUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend fun getRecipes(query:String): RecipesResponse =
        apiRepository.getRecipes(query)

    suspend fun getRecipeInfo(id:String): RecipeInfo =
        apiRepository.getRecipesInfo(id)

    suspend fun getIngredients(query: String): IngredientResponse =
        apiRepository.getIngredients(query)

    suspend fun getIngredientInfo(id: String): IngredientInfo =
        apiRepository.getIngredientInfo(id)
}