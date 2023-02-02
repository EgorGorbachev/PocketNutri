package com.example.pocketnutri.data.source.rest.api

import com.example.pocketnutri.data.constants.KEY
import com.example.pocketnutri.data.source.rest.models.ingredient_details.IngredientInfo
import com.example.pocketnutri.data.source.rest.models.ingredients.IngredientResponse
import com.example.pocketnutri.data.source.rest.models.recipes.RecipesResponse
import com.example.pocketnutri.data.source.rest.models.recipes_details.RecipeInfo
import retrofit2.http.*

interface SpoonacularApi {


//    @GET("/recipes/complexSearch")
//    @Headers("Application: application/json")
//    suspend fun getRecipes(
//        @QueryMap queries: Map<String, String>,
//        @Query("apiKey")key: String = KEY
//    ): Response<RecipesModel>

    @GET("/recipes/complexSearch")
    @Headers("Application: application/json")
    suspend fun getRecipes(
        @Query("query")query:String,
        @Query("apiKey")key: String = KEY
    ): RecipesResponse

    @GET("/recipes/{id}/information")
    @Headers("Application: application/json")
    suspend fun getRecipeInfo(
        @Path("id")id:String,
        @Query("apiKey")key:String = KEY
    ): RecipeInfo

    @GET("/food/ingredients/search?")
    @Headers("Application: application/json")
    suspend fun getIngredients(
        @Query("query")query:String,
        @Query("apiKey")key:String = KEY
    ): IngredientResponse

    @GET("/food/ingredients/{id}/information")
    @Headers("Application: application/json")
    suspend fun getIngredientInfo(
        @Path("id")id:String,
        @Query("amount")amount:Int = 1,
        @Query("apiKey")key:String = KEY
    ): IngredientInfo
}