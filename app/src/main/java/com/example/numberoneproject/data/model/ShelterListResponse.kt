package com.example.numberoneproject.data.model

data class ShelterListResponse(
    val count: Int = 0,
    val shelterList: List<Shelter> = listOf()
)

data class Shelter(
    val distance: String = "",
    val facilityName: String = "",
    val id: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)