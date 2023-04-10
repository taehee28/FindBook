package com.thk.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thk.data.models.BookEntity
import com.thk.data.remote.BookApiInterface
import com.thk.data.utils.logd
import retrofit2.HttpException

private const val STARTING = 1
private const val PAGE_UNIT = 10

class BookPagingSource(
    private val keyword: String,
    private val remoteApi: BookApiInterface
) : PagingSource<Int, BookEntity>() {
    override fun getRefreshKey(state: PagingState<Int, BookEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_UNIT) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_UNIT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookEntity> {
        return try {

            val start = params.key ?: STARTING

            val response = remoteApi.getSearchResult(keyword = keyword, start = start)
            logd(">> response = $response")
            val list = response.items

            val prevKey = if (start > 1) start.minus(PAGE_UNIT) else null
            val nextKey = if (list.isEmpty()) null else start.plus(PAGE_UNIT)

            LoadResult.Page(
                data = list,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}