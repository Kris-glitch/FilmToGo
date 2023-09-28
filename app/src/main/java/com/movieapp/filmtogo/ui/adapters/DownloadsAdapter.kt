package com.movieapp.filmtogo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.FavoritesDownloadsItemBinding
import com.movieapp.filmtogo.modelLocal.LocalMovies

class DownloadsAdapter() : RecyclerView.Adapter<DownloadsAdapter.DownloadsViewHolder>() {


    private var movieList : ArrayList<LocalMovies> = arrayListOf()

    private val differ : AsyncListDiffer<LocalMovies> = AsyncListDiffer(this, DownloadedMovieDiffCallback())

    fun updateDataset(newMovieList : ArrayList<LocalMovies>) {
        differ.submitList(newMovieList)
        movieList = newMovieList
    }

    fun delete(position : Int){
        movieList.removeAt(position)
    }


    inner class DownloadsViewHolder (val binding : FavoritesDownloadsItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind (movie : LocalMovies){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : FavoritesDownloadsItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.favorites_downloads_item, parent, false)
        return DownloadsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: DownloadsViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    class DownloadedMovieDiffCallback : DiffUtil.ItemCallback<LocalMovies>() {
        override fun areItemsTheSame(oldItem: LocalMovies, newItem: LocalMovies): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.poster_path == newItem.poster_path
                    && oldItem.release_date == newItem.release_date
                    && oldItem.title == newItem.title
                    && oldItem.vote_average == newItem.vote_average
        }
        override fun areContentsTheSame(oldItem: LocalMovies, newItem: LocalMovies): Boolean {
            return oldItem == newItem
        }
    }


}