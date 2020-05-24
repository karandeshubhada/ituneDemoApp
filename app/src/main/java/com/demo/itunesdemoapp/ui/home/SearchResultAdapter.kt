package com.demo.itunesdemoapp.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.demo.itunesdemoapp.databinding.ItemSearchedMovieBinding
import com.demo.itunesdemoapp.source.cache.MovieEntity
import com.demo.itunesdemoapp.ui.home.HomeViewModel
import com.demo.itunesdemoapp.ui.home.SearchResultViewHolder
import com.squareup.picasso.Picasso
import java.text.NumberFormat

private object DifferSearchMovie : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }
}

class SearchedMoviesAdapter(private val viewModel: HomeViewModel) :
    PagedListAdapter<MovieEntity, SearchResultViewHolder>(DifferSearchMovie) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemSearchedMovieBinding.inflate(inflater, parent, false)
        view.parent.setOnClickListener {
            view.movie?.let {
                viewModel.openMovie(it.id)
            }
        }
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        // When null it means SearchResultViewHolder is a Placeholder
        if (item != null) {
            holder.init(item)
        } else {
            holder.clear()
        }
    }

}