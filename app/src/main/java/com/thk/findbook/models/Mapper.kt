package com.thk.findbook.models

import com.thk.data.models.BookEntity

fun BookEntity.toBook(): Book = Book(
    link = this.link,
    imageUrl = this.image,
    title = this.title,
    author = this.author,
    publisher = this.publisher,
    discount = this.discount
)