package com.demo.itunesdemoapp.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.demo.itunesdemoapp.R
import com.demo.itunesdemoapp.databinding.ItemSearchedMovieBinding
import com.demo.itunesdemoapp.databinding.ItemWatchedMovieBinding
import com.demo.itunesdemoapp.source.cache.MovieEntity
import com.demo.itunesdemoapp.util.toCurrency
import com.squareup.picasso.Picasso


class SearchResultViewHolder(private val binder: ItemSearchedMovieBinding) : RecyclerView.ViewHolder(binder.root) {


    fun init(item: MovieEntity) {
        binder.movie = item
        binder.apply {
            movieTitle.text = item.name
            price.text = item.price.toCurrency(item.currency)
            movieShortDesc.text = item.shortDesc
            genre.text = item.genre
            Picasso.get()
                .load(item.image)
                .placeholder(R.drawable.ic_film)
                .centerCrop()
                .fit()
                .into(movieImage)
        }
    }

    fun clear() {
        binder.movie = null
        binder.apply {
            movieImage.setImageBitmap(null)
            movieTitle.text = null
            price.text = null
            movieShortDesc.text = null
            genre.text = null
        }
    }
}


class WatchResultViewHolder(val binder: ItemWatchedMovieBinding) : RecyclerView.ViewHolder(binder.root)