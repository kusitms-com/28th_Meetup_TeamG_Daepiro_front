package com.daepiro.numberoneproject.data.model

data class CommentWritingResponse(
    val address: String,
    val articleId: Int,
    val createdAt: String,
    val imageUrls: List<String>,
    val thumbNailImageUrl: String
)