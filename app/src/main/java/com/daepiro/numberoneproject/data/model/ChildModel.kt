package com.daepiro.numberoneproject.data.model

data class Child(
    val childs: List<Any>,
    val content: String,
    val conversationId: Int,
    val info: String,
    val isEditable: Boolean,
    val isLiked: Boolean,
    val like: Int
)