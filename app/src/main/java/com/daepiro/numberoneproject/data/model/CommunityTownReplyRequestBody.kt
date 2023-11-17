package com.daepiro.numberoneproject.data.model

data class CommunityTownReplyRequestBody(
    val content: String = "",
    val latitude: Double=0.0,
    val longitude: Double=0.0
)