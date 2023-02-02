package com.example.pocketnutri.domain.repository

import com.example.pocketnutri.data.source.rest.models.ingredient_details.IngredientInfo
import com.example.pocketnutri.data.source.rest.models.ingredients.IngredientResponse
import com.example.pocketnutri.data.source.rest.models.recipes.RecipesResponse
import com.example.pocketnutri.data.source.rest.models.recipes_details.RecipeInfo

interface ApiRepository {

    suspend fun getRecipes(query: String): RecipesResponse
    suspend fun getRecipesInfo(id: String): RecipeInfo
    suspend fun getIngredients(query: String): IngredientResponse
    suspend fun getIngredientInfo(id: String): IngredientInfo
}