package com.demo.itunesdemoapp.source.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.itunesdemoapp.AppApplication
import com.demo.itunesdemoapp.source.cache.dao.MovieDao
import com.demo.itunesdemoapp.source.cache.dao.WatchHistoryDao

@Database(
    entities = [MovieEntity::class,
        SearchResultsEntity::class,
        WatchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        DateConverter::class,
        UriConverter::class
    ]
)
abstract class ItunesMovieCache : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun watchHistoryDao(): WatchHistoryDao

    companion object {
        const val DATABASE_NAME = "ItunesMovieCache"


        val database = Room.databaseBuilder(
            AppApplication.globalContext,
            ItunesMovieCache::class.java,
            DATABASE_NAME
        ).build()
    }
}
