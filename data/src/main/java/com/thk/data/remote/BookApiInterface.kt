package com.thk.data.remote

import com.thk.data.models.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiInterface {
    @GET("book.json")
    suspend fun getSearchResult(
        @Query("query") keyword: String,
        @Query("start") pageIndex: Int = 1
    ): BookSearchResponse
}