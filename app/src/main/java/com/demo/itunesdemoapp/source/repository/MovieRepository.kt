package com.demo.itunesdemoapp.source.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import com.demo.itunesdemoapp.IO_EXECUTOR
import com.demo.itunesdemoapp.source.api.Api
import com.demo.itunesdemoapp.source.boundary.BoundaryBundle
import com.demo.itunesdemoapp.source.boundary.MovieBoundary
import com.demo.itunesdemoapp.source.cache.ItunesMovieCache
import com.demo.itunesdemoapp.source.cache.MovieEntity
import com.demo.itunesdemoapp.source.cache.WatchHistoryEntity

import java.util.*

class MovieRepository {
    private val api = Api.api
    private val db = ItunesMovieCache.database

    fun getMovies(keyword: String, itemCount: Int = 50): BoundaryBundle<MovieEntity> {
        val movieBoundary = MovieBoundary(keyword, api, db, itemCount)
        val movieFactory = db.movieDao().getMoviesByKeyword(keyword)
        val pageList = LivePagedListBuilder<Int, MovieEntity>(movieFactory, itemCount).apply {
            setBoundaryCallback(movieBoundary)
        }.build()
        return BoundaryBundle(pageList, movieBoundary.itemCountSignal)
    }

    fun getMovie(movieId: String): LiveData<MovieEntity> {
        IO_EXECUTOR.execute {
            db.watchHistoryDao().insert(WatchHistoryEntity(movieId, Date()))
        }
        return db.movieDao().getMovie(movieId)
    }

    fun getWatchHistory() = db.watchHistoryDao().getMovieHistory()
}