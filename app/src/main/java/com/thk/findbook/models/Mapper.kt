package com.thk.findbook.models

import com.thk.data.models.BookEntity
import com.thk.data.models.RecentSearchEntity

/**
 * data 모듈에서 쓰이는 데이터 타입을
 * app 모듈에서 사용하는 데이터 타입으로 변환
 */

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