package com.daepiro.numberoneproject.data.model

data class Situation(
    val conversationCnt: Int,
    val conversations: List<Conversation>,
    val disasterId: Int,
    val disasterType: String,
    val info: String,
    val msg: String,
    val title: String
)