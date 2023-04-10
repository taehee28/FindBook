package com.thk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thk.data.models.RecentSearchEntity

@Database(
    version = 1,
    entities = [RecentSearchEntity::class],
    exportSchema = false
)
abstract class RecentSearchesDatabase : RoomDatabase() {
    abstract fun recentSearchesDao(): RecentSearchesDao

    companion object {
        @Volatile
        private var instance: RecentSearchesDatabase? = null

        fun getInstance(context: Context): RecentSearchesDatabase {
            return instance ?: kotlin.run {
                synchronized(this) {
                    val tempInstance = Room.databaseBuilder(
                        context,
                        RecentSearchesDatabase::class.java,
                        DBInfo.DB_NAME
                    ).build()

                    instance = tempInstance
                    tempInstance
                }
            }
        }
    }
}