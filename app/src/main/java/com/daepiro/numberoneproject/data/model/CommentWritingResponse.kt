package com.daepiro.numberoneproject.data.model

import okhttp3.MultipartBody

data class CommentWritingResponse(
    val address: String = "",
    val articleId: Int = 0,
    val createdAt: String="",
    val imageUrls: List<MultipartBody.Part> = listOf(),
    val thumbNailImageUrl: String=""
)