package com.daepiro.numberoneproject.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FamilyListResponse(
    val friendMemberId: Int = 0,
    val isSafety: Boolean = false,
    val location: String = "",
    val nickName: String = "",
    val profileImageUrl: String = "",
    val realName: String = "",
    val session: Boolean = false
): Parcelable