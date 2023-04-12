package com.thk.data.models

/**
 * 책 정보 데이터 클래스
 */
data class BookEntity(
    val title: String,
    val link: String,
    val image: String,
    val author: String,
    val publisher: String,
    val discount: String
)
