package com.thk.data.models

data class BookSearchResponse(
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<BookEntity>
)
