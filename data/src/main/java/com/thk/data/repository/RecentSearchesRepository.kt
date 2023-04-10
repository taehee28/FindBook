package com.thk.data.repository

import com.thk.data.models.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

interface RecentSearchesRepository {
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>
    suspend fun removeAll()
}