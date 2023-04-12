package com.thk.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thk.data.local.RecentSearchesDao
import com.thk.data.local.RecentSearchesDatabase
import com.thk.data.models.BookEntity
import com.thk.data.models.RecentSearchEntity
import com.thk.data.paging.BookPagingSource
import com.thk.data.remote.BookApiInterface
import com.thk.data.utils.logd
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * 책 검색 Repository
 */
interface SearchRepository {
    suspend fun searchBook(keyword: String): Flow<PagingData<BookEntity>>
}

class SearchRepositoryImpl @Inject constructor(
    private val remoteApi: BookApiInterface,
    database: RecentSearchesDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {
    private val dao = database.recentSearchesDao()

    override suspend fun searchBook(keyword: String): Flow<PagingData<BookEntity>> {
        withContext(ioDispatcher) {
            // 검색한 키워드를 저장하고
            dao.insertRecentSearch(RecentSearchEntity.of(keyword))

            // 개수를 확인해서 10개가 넘으면
            // 오래된 키워드를 삭제
            val count = dao.getRowCount()
            if (count > 10) {
                dao.deleteOverflows()
            }
        }

        // PagingData를 리턴
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                BookPagingSource(keyword = keyword, remoteApi = remoteApi)
            }
        ).flow
    }
}