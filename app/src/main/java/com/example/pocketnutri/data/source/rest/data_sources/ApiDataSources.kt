package com.example.pocketnutri.data.source.rest.data_sources

import com.example.pocketnutri.data.source.rest.api.SpoonacularApi
import com.example.pocketnutri.data.source.rest.models.ingredient_details.IngredientInfo
import com.example.pocketnutri.data.source.rest.models.ingredients.IngredientResponse
import com.example.pocketnutri.data.source.rest.models.recipes.RecipesResponse
import com.example.pocketnutri.data.source.rest.models.recipes_details.RecipeInfo
import javax.inject.Inject

class ApiDataSources @Inject constructor(
    private val spoonacularApi: SpoonacularApi
){
    suspend fun getRecipes(query: String): RecipesResponse =
        spoonacularApi.getRecipes(query)

    suspend fun getRecipeInfo(id:String): RecipeInfo =
        spoonacularApi.getRecipeInfo(id)

    suspend fun getIngredients(query:String): IngredientResponse =
        spoonacularApi.getIngredients(query)

    suspend fun getIngredientsInfo(id:String): IngredientInfo =
        spoonacularApi.getIngredientInfo(id)
}
