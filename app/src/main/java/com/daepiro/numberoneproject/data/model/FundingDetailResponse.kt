package com.daepiro.numberoneproject.data.model

data class FundingDetailResponse(
    val content: String = "",
    val currentHeart: Int = 0,
    val dday: String = "",
    val disasterType: String = "",
    val id: Int = 0,
    val isSupported: Boolean = false,
    val period: String = "",
    val sponsorName: String = "",
    val subtitle: String = "",
    val targetHeart: Int = 1,
    val title: String = "",
    val imageUrl: String = "",
    val sponsorUrl: String = ""
)