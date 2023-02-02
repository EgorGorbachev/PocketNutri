package com.example.pocketnutri.data.repository

import com.example.pocketnutri.data.source.rest.data_sources.ApiDataSources
import com.example.pocketnutri.data.source.rest.models.ingredient_details.IngredientInfo
import com.example.pocketnutri.data.source.rest.models.ingredients.IngredientResponse
import com.example.pocketnutri.data.source.rest.models.recipes.RecipesResponse
import com.example.pocketnutri.data.source.rest.models.recipes_details.RecipeInfo
import com.example.pocketnutri.domain.repository.ApiRepository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiDataSources: ApiDataSources
) : ApiRepository {

    override suspend fun getRecipes(query: String): RecipesResponse =
        apiDataSources.getRecipes(query)

    override suspend fun getRecipesInfo(id: String): RecipeInfo =
        apiDataSources.getRecipeInfo(id)

    override suspend fun getIngredients(query: String): IngredientResponse =
        apiDataSources.getIngredients(query)

    override suspend fun getIngredientInfo(id: String): IngredientInfo =
        apiDataSources.getIngredientsInfo(id)
}