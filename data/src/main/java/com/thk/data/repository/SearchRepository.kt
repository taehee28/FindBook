package com.thk.data.repository

import com.thk.data.models.BookEntity
import com.thk.data.remote.BookApiInterface
import com.thk.data.utils.logd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SearchRepository {
    fun searchBook(keyword: String): Flow<List<BookEntity>>
    suspend fun insertRecentSearch(keyword: String)
}

class SearchRepositoryImpl @Inject constructor(
    private val remoteApi: BookApiInterface
) : SearchRepository {
    override fun searchBook(keyword: String): Flow<List<BookEntity>> = flow {
        val response = remoteApi.getSearchResult(keyword)
        logd(">> response = $response")

        emit(response.items)
    }

    override suspend fun insertRecentSearch(keyword: String) {

    }
}