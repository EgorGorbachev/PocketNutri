package com.example.pocketnutri.data.source.rest.models.recipes_details

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatche>
)