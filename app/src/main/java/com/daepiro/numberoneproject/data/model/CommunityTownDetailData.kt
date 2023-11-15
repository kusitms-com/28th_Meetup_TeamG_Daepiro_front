package com.daepiro.numberoneproject.data.model

data class CommunityTownDetailData(
    val address: String="",
    val articleId: Int =0,
    val content: String="",
    val createdAt: String="",
    val imageUrls: List<String>? = null,
    val likeCount: Int=0,
    val memberName: String="",
    val memberNickName: String="",
    val modifiedAt: String="",
    val ownerMemberId: Int=0,
    val thumbNailImageUrl: String = "",
    val title: String=""
)