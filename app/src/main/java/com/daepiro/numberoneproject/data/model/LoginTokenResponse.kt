package com.daepiro.numberoneproject.data.model

data class LoginTokenResponse(
    val accessToken: String = "",
    val refreshToken: String = "",
    val isNewMember:Boolean = true
)
