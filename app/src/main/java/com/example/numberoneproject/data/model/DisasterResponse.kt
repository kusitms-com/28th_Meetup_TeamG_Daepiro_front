package com.example.numberoneproject.data.model

data class DisasterResponse(
    val disasterType: String = "",
    val severity: Int = 0,
    val title: String = "",
    val msg: String = "",
    val info: String = ""
)