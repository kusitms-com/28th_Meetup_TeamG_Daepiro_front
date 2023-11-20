package com.daepiro.numberoneproject.data.model

data class Conversation(
    val childs: List<Child>,
    val content: String,
    val conversationId: Int,
    val info: String,
    val isEditable: Boolean,
    val isLiked: Boolean,
    val like: Int
)