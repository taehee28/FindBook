package com.thk.data.repository

import com.thk.data.local.RecentSearchesDao
import com.thk.data.local.RecentSearchesDatabase
import com.thk.data.models.RecentSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RecentSearchesRepository {
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>
    suspend fun removeAll()
}

class RecentSearchesRepositoryImpl @Inject constructor(
    database: RecentSearchesDatabase
) : RecentSearchesRepository {
    private val dao = database.recentSearchesDao()

    override fun getRecentSearches(): Flow<List<RecentSearchEntity>> =
        dao.getRecentSearches()

    override suspend fun removeAll() = dao.deleteAll()
}