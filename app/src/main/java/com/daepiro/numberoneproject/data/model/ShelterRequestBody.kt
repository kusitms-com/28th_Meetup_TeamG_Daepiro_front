package com.daepiro.numberoneproject.data.model

data class ShelterRequestBody(
    val latitude: Double,
    val longitude: Double,
    val shelterType: String? = ""
)
