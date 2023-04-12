package com.thk.data.repository

import com.thk.data.local.RecentSearchesDatabase
import com.thk.data.models.RecentSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 최근 검색어에 대한 Repository
 */
interface RecentSearchesRepository {
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>
    suspend fun deleteAll()
}

class RecentSearchesRepositoryImpl @Inject constructor(
    database: RecentSearchesDatabase
) : RecentSearchesRepository {
    private val dao = database.recentSearchesDao()

    override fun getRecentSearches(): Flow<List<RecentSearchEntity>> =
        dao.getRecentSearches()

    override suspend fun deleteAll() = dao.deleteAll()
}