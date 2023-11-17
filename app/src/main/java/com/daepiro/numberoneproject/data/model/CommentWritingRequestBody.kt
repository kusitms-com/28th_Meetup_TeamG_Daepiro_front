package com.daepiro.numberoneproject.data.model

data class CommentWritingRequestBody(
    val articleTag: String,
    val content: String,
    val imageList: List<String>,
    val latitude: Int,
    val longitude: Int,
    val title: String
)