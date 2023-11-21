package com.daepiro.numberoneproject.data.model

data class InitDataOnBoardingRequest(
    val addresses: List<AddresseModel>,
    val disasterTypes: List<DisasterTypeModel>,
    val fcmToken: String,
    val nickname: String,
    val realname: String
)