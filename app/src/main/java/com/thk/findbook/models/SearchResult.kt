package com.thk.findbook.models

data class SearchResult(
    val link: String,
    val imageUrl: String,
    val title: String,
    val author: String,
    val publisher: String,
    val discount: String
)
