package com.example.pocketnutri.data.source.rest.models.ingredient_details

data class IngredientInfo(
    val id: Int,
    val name: String,
    val categoryPath: List<String>,
    val image: String,
    val nutrition: Nutritious,
)

data class Nutritious(
    val nutrients: List<Nutrition>,
    val caloricBreakdown: CaloricBreakdown
)