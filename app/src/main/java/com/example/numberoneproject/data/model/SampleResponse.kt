package com.example.numberoneproject.data.model

data class SampleResponse(
    val data: List<Data> = emptyList(),
    val isSuccess: Boolean = false,
    val message: String = ""
)

data class Data(
    val articleId: Int = 0,
    val body: String = "",
    val createdAt: String = "",
    val description: String = "",
    val tagList: List<String> = emptyList(),
    val title: String = "",
    val updatedAt: String = ""
)