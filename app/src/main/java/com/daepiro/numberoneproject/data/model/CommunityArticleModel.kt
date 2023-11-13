package com.daepiro.numberoneproject.data.model

data class CommunityArticleModel(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX
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
data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: SortX,
    val unpaged: Boolean
)
data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)