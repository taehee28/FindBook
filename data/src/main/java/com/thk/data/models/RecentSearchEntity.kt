package com.thk.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thk.data.local.DBInfo

/**
 * 최근 검색어 데이터 클래스
 */
@Entity(
    tableName = DBInfo.TABLE_NAME,
    indices = [
        Index(value = ["keyword"], unique = true)
    ]
)
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val keyword: String = ""
) {
    companion object {
        fun of(value: String) = RecentSearchEntity(keyword = value)
    }
}
