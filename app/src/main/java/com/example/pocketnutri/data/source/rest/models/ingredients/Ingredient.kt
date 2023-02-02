package com.example.pocketnutri.data.source.rest.models.ingredients

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val id: String,
    val name: String,
    val image: String,
) : Parcelable
