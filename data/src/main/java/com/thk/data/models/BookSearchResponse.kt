package com.thk.data.models

/**
 * 책 검색 API 호출에 대한 응답 데이터 클래스
 */
data class BookSearchResponse(
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<BookEntity>
)
