package com.demo.itunesdemoapp.source.boundary

import androidx.core.net.toUri
import androidx.paging.PagedList
import com.demo.itunesdemoapp.IO_EXECUTOR
import com.demo.itunesdemoapp.NETWORK_EXECUTOR
import com.demo.itunesdemoapp.source.api.ApiService
import com.demo.itunesdemoapp.source.api.Movie
import com.demo.itunesdemoapp.source.cache.ItunesMovieCache
import com.demo.itunesdemoapp.source.cache.MovieEntity
import com.demo.itunesdemoapp.source.cache.SearchResultsEntity
import com.demo.itunesdemoapp.util.toDate

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MovieBoundary(
    private val searchKeyword: String,
    private val api: ApiService,
    private val db: ItunesMovieCache,
    private val downloadCount: Int
) : PagedList.BoundaryCallback<MovieEntity>() {

    private var itemCount = 0
    private val movieDao = db.movieDao()
    val helper = PagingRequestHelper(IO_EXECUTOR)

    val itemCountSignal: (Int) -> Unit = {
        itemCount = it
    }

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            NETWORK_EXECUTOR.execute {
                api.searchMoviesFromAustralia(
                    keyword = searchKeyword,
                    limit = downloadCount
                ).callbackSuccess(it) {
                    db.runInTransaction {
                        val movieEntities = it.body()?.movies?.map(Movie::toEntity)
                        movieEntities?.run { movieDao.insertMovies(this) }
                        val searchEntities = movieEntities?.map { SearchResultsEntity(searchKeyword, it.id) }
                        searchEntities?.run { movieDao.insertSearches(this) }
                    }
                }
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: MovieEntity) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            NETWORK_EXECUTOR.execute {
                api.searchMoviesFromAustralia(
                    keyword = searchKeyword,
                    limit = downloadCount,
                    offset = itemCount.toOffset(downloadCount)
                ).callbackSuccess(it) {
                    db.runInTransaction {
                        val movieEntities = it.body()?.movies?.map(Movie::toEntity)
                        movieEntities?.run { movieDao.insertMovies(this) }
                        val searchEntities = movieEntities?.map { SearchResultsEntity(searchKeyword, it.id) }
                        searchEntities?.run { movieDao.insertSearches(this) }
                    }
                }
            }
        }
    }
}

private inline fun <T> Call<T>.callbackSuccess(callback: PagingRequestHelper.Callback, func: (Response<T>) -> Unit) {
    try {
        val response = execute()
        if (response.isSuccessful) {
            func(response)
            callback.recordSuccess()
        } else {
            callback.recordFailure(HttpException(response))
        }
    } catch (ex: IOException) {

        callback.recordFailure(ex)
    }
}


private fun Movie.toEntity() = MovieEntity(
    trackId.toString(),
    this!!.trackName.toString(),
    trackPrice ?: 0.0,
    currency,
    shortDescription,
    longDescription,
    releaseDate!!.toDate(),
    primaryGenreName!!,
    artistName!!,
    previewUrl?.toUri(),
    artworkUrl100?.replace("100x100", "600x600")?.toUri() // we need high res image
)


private fun Int.toOffset(limit: Int) = if (this % limit == 0) {
    this / limit
} else {
    (this / limit) + 1
}
