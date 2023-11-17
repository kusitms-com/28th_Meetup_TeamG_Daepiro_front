package com.daepiro.numberoneproject.data.model

data class CommentWritingRequestBody(
    val articleTag: String = "",
    val content: String="",
    val latitude: Int = 0,
    val longitude: Int=0,
    val title: String=""
)