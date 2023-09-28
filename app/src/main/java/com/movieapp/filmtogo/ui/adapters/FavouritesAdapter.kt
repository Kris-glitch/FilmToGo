package com.movieapp.filmtogo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.FavoritesDownloadsItemBinding
import com.movieapp.filmtogo.modelRemote.Movie


class FavouritesAdapter: RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>()  {

    private var movieList : ArrayList<Movie> = arrayListOf()

    private val differ : AsyncListDiffer<Movie> = AsyncListDiffer(this, MovieDiffCallback())

    fun updateDataset(newMovieList : ArrayList<Movie>) {
        differ.submitList(newMovieList)
        movieList = newMovieList
    }

    fun delete(position : Int){
        movieList.removeAt(position)
    }

    inner class FavouritesViewHolder (val binding : FavoritesDownloadsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (movie : Movie){
            binding.apply {
                downMovieTitle.text = movie.title
                downMovieYear.text = movie.release_date
                downMovieRating.text = movie.vote_average.toString()
                if (!movie.poster_path.isNullOrEmpty()){
                    Glide.with(binding.root.context).load("https://image.tmdb.org/t/p/w500/"
                            + movie.poster_path).into(binding.downMovieImage)
                }
                binding.executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : FavoritesDownloadsItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.favorites_downloads_item, parent, false)
        return FavouritesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }
}