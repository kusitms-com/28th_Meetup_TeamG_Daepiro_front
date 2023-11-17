package com.daepiro.numberoneproject.data.model

data class CommunityTownReplyResponseItem(
    val authorId: Int=0,
    val authorNickName: String="",
    val authorProfileImageUrl: String="",
    val childComments: List<Any>?,
    val commentId: Long=0,
    val content: String = "",
    val createdAt: String = "",
    val likeCount: Int = 0,
    val liked: Boolean=false,
    val modifiedAt: String="",
    val parentCommentId: Long?
)