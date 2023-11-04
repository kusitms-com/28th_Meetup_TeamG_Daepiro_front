package com.example.numberoneproject.data.model

data class ShelterData(
    val addressDetail: AddressDetail,
    val floods: List<Any>,
    val civils: List<Civil>,
    val earthquakes: List<Any>
)
data class AddressDetail(
    val city: String,
    val district: String,
    val dong: String
)

data class Civil(
    val id: Int,
    val fullAddress: String,
    val facilityFullName: String,
    val longitude: Double,
    val latitude: Double
)

