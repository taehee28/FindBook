package com.thk.data.local

import androidx.room.*
import com.thk.data.models.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(item: RecentSearchEntity)

    @Query("SELECT * FROM ${DBInfo.TABLE_NAME} ORDER BY id DESC LIMIT 10")
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>

    @Query("SELECT COUNT(*) FROM ${DBInfo.TABLE_NAME}")
    suspend fun getRowCount(): Int

    @Query("DELETE FROM ${DBInfo.TABLE_NAME} where id NOT IN (SELECT id FROM ${DBInfo.TABLE_NAME} ORDER BY id DESC LIMIT 10)")
    suspend fun deleteOverflows()

    @Query("DELETE FROM ${DBInfo.TABLE_NAME}")
    suspend fun deleteAll()
}