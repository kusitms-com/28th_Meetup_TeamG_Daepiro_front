package com.daepiro.numberoneproject.data.model

data class FundingListResponse(
    val messages: List<String> = emptyList(),
    val nickname: String = "",
    val sponsors: List<FundingInfo> = emptyList(),
    val supporterCnt: Int = 0
)

data class FundingInfo(
    val content: String = "",
    val currentHeart: Int = 0,
    val dday: String = "",
    val disasterType: String = "",
    val id: Int = 0,
    val isSupported: Boolean = false,
    val period: String = "",
    val sponsorName: String = "",
    val subtitle: String = "",
    val targetHeart: Int = 0,
    val title: String = "",
    val thumbnailUrl: String? = ""
)