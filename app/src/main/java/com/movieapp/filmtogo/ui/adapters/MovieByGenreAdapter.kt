package com.movieapp.filmtogo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.GenresItemBinding
import com.movieapp.filmtogo.modelRemote.Movie

class MovieByGenreAdapter(private val onItemClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieByGenreAdapter.MoviesByGenreViewHolder>() {

    private var selectedMovie: Movie? = null
    private var movieList : List<Movie> = emptyList()
    private val differ : AsyncListDiffer<Movie> = AsyncListDiffer(this, MovieDiffCallback())

    fun updateDataset(newMovieList : List<Movie>) {
        differ.submitList(newMovieList)
        movieList = newMovieList
    }


    inner class MoviesByGenreViewHolder(val binding : GenresItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                val clickedItem = movieList[adapterPosition]
                selectedMovie = clickedItem
                notifyDataSetChanged()
                onItemClick(clickedItem)
            }
        }

        fun bind (movie : Movie){
            binding.movieTitle.text = movie.title
            if (!movie.poster_path.isNullOrEmpty()) {
                Glide.with(binding.root.context).load(
                    "https://image.tmdb.org/t/p/w500/"
                            + movie.poster_path
                ).into(binding.movieImage)
            }
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesByGenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : GenresItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.genres_item, parent,false)
        return MoviesByGenreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MoviesByGenreViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

}