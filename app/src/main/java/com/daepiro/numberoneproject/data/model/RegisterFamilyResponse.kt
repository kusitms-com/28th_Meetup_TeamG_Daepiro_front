package com.daepiro.numberoneproject.data.model

data class RegisterFamilyResponse(
    val createdAt: String = "",
    val invitedMemberId: Int = 0,
    val invitingMemberId: Int = 0
)