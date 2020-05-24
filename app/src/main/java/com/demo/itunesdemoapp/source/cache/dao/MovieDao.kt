package com.demo.itunesdemoapp.source.cache.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.itunesdemoapp.source.cache.MovieEntity
import com.demo.itunesdemoapp.source.cache.SearchResultsEntity

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertSearches(genres: List<SearchResultsEntity>)

    @Query("SELECT movies.* FROM movies INNER JOIN search_results ON movies.movie_id = search_results.movie_id WHERE search_results.keyword = :keyword")
    abstract fun getMoviesByKeyword(keyword: String): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    abstract fun getMovie(movieId: String): LiveData<MovieEntity>
}