package com.thk.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thk.data.models.BookEntity
import com.thk.data.paging.BookPagingSource
import com.thk.data.remote.BookApiInterface
import com.thk.data.utils.logd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SearchRepository {
    fun searchBook(keyword: String): Flow<PagingData<BookEntity>>
    suspend fun insertRecentSearch(keyword: String)
}

class SearchRepositoryImpl @Inject constructor(
    private val remoteApi: BookApiInterface
) : SearchRepository {
    override fun searchBook(keyword: String): Flow<PagingData<BookEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                BookPagingSource(keyword = keyword, remoteApi = remoteApi)
            }
        ).flow
    }

    override suspend fun insertRecentSearch(keyword: String) {

    }
}