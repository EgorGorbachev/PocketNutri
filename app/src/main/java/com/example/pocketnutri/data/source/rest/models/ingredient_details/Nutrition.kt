package com.example.pocketnutri.data.source.rest.models.ingredient_details

data class Nutrition(
    val name: String,
    val amount:Double,
    val percentOfDailyNeeds:String
)