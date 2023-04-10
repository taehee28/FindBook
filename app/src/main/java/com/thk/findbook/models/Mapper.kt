package com.thk.findbook.models

import com.thk.data.models.BookEntity
import com.thk.data.models.RecentSearchEntity

fun BookEntity.toBook(): Book = Book(
    link = this.link,
    imageUrl = this.image,
    title = this.title,
    author = this.author,
    publisher = this.publisher,
    discount = this.discount
)

fun RecentSearchEntity.toRecentSearch(): RecentSearch = RecentSearch(
    id = this.id,
    keyword = this.keyword
)