package com.example.numberoneproject.data.model

import android.content.Context
import android.location.Geocoder
import java.util.Locale

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
) {
    fun changeLatLogToAddress(context: Context): String {
        val geocoder = Geocoder(context, Locale.KOREAN)
        val address = geocoder.getFromLocation(latitude, longitude, 1)

        return address?.get(0)?.getAddressLine(0).toString().replace("대한민국 ", "")
    }
}