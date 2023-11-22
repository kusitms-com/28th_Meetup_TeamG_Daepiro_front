package com.daepiro.numberoneproject.data.model

data class CommunityHomeConversationModel(
    val content: String = "",
    val conversationId: Int = 0,
    val info: String = "",
    val isEditable: Boolean = false,
    val isLiked: Boolean = false,
    val like: Int=0
)