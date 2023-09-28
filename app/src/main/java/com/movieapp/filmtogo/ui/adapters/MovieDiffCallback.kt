package com.movieapp.filmtogo.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.movieapp.filmtogo.modelRemote.Movie

class MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
                && oldItem.original_language == newItem.original_language
                && oldItem.overview == newItem.overview
                && oldItem.popularity == newItem.popularity
                && oldItem.poster_path == newItem.poster_path
                && oldItem.release_date == newItem.release_date
                && oldItem.title == newItem.title
                && oldItem.vote_average == newItem.vote_average
    }
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
