package com.example.pocketnutri.data.source.rest.models.ingredients

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientResponse(
    val results: List<Ingredient>
) : Parcelable



