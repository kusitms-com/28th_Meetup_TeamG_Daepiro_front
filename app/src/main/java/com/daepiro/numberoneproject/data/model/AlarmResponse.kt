package com.daepiro.numberoneproject.data.model

data class AlarmResponse(
    val content: List<Contents> = emptyList(),
    val empty: Boolean = false,
    val first: Boolean = false,
    val last: Boolean = false,
    val number: Int = 0,
    val numberOfElements: Int = 0,
    val pageable: Pageable = Pageable(),
    val size: Int = 0,
    val sort: PageableSort = PageableSort()
)

data class Contents(
    val body: String = "",
    val createdAt: String = "",
    val disasterTagDetail: String = "",
    val id: Int = 0,
    val tag: String = "",
    val title: String = "",
    val location: String = "",
    val timeText: String = ""
)

data class Pageable(
    val offset: Int = 0,
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val paged: Boolean = false,
    val sort: PageableSort = PageableSort(),
    val unpaged: Boolean = false
)


data class PageableSort(
    val empty: Boolean = false,
    val sorted: Boolean = false,
    val unsorted: Boolean = false
)