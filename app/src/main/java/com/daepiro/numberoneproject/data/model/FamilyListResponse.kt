package com.daepiro.numberoneproject.data.model

data class FamilyListResponse(
    val friendMemberId: Int = 0,
    val isSafety: Boolean = false,
    val location: String = "",
    val nickName: String = "",
    val profileImageUrl: String = "",
    val realName: String = "",
    val session: Boolean = false
)