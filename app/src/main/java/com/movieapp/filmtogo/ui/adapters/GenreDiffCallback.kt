package com.movieapp.filmtogo.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.movieapp.filmtogo.modelRemote.Genre

class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {

    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }
}