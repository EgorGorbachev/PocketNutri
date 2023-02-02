package com.example.pocketnutri.data.source.rest.models.recipes_details

data class ExtendedIngredient(
    val aisle: String,
    val amount: Double,
    val id: Int,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String
)