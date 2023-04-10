package com.thk.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thk.data.local.DBInfo

@Entity(tableName = DBInfo.TABLE_NAME)
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val keyword: String = ""
) {
    companion object {
        fun of(value: String) = RecentSearchEntity(keyword = value)
    }
}
