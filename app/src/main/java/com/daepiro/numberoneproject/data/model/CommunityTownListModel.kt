package com.daepiro.numberoneproject.data.model

data class CommunityTownListModel(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: String?,
    val size: Int,
    val sort: Sort
)
data class Content(
    val address: String,
    val articleLikeCount: Int,
    val articleStatus: String,
    val commentCount: Int,
    val content: String,
    val createdAt: String,
    val id: Int,
    val ownerId: Int,
    val ownerNickName: String,
    val tag: String,
    val thumbNailImageId: Int,
    val thumbNailImageUrl: String,
    val title: String
)
//data class Pageable(
//    val offset: Int,
//    val pageNumber: Int,
//    val pageSize: Int,
//    val paged: Boolean,
//    val sort: Sort,
//    val unpaged: Boolean
//)
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)