package com.movieapp.filmtogo.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.RecomendedItemBinding
import com.movieapp.filmtogo.modelRemote.Movie
import com.movieapp.filmtogo.ui.fragments.HomepageFragmentDirections

class RecommendedAdapter (private val navController: NavController,
                          private val onItemClick: (Movie) -> Unit) : RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder>() {

    private var selectedMovie: Movie? = null
    private var movieList : List<Movie> = emptyList()
    private val differ : AsyncListDiffer<Movie> = AsyncListDiffer(this, MovieDiffCallback())
    fun updateDataset(newMovieList : List<Movie>) {
        differ.submitList(newMovieList)
        movieList = newMovieList
    }
    inner class RecommendedViewHolder(val binding : RecomendedItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                val clickedItem = movieList[adapterPosition]
                selectedMovie = clickedItem
                notifyDataSetChanged()
                onItemClick(clickedItem)
            }

            binding.playNowBtn.setOnClickListener {
                navController.navigate(HomepageFragmentDirections.actionHomepageFragmentToMoviePlayFragment())
            }
        }


        fun bind (movie : Movie){
            binding.recommendedMovieTitle.text = movie.title
            binding.recommendedMovieRating.text = movie.vote_average.toString()
            binding.recommendedMovieYear.text = movie.release_date
            if (!movie.poster_path.isNullOrEmpty()){
                Glide.with(binding.root.context).load("https://image.tmdb.org/t/p/w500/"
                    + movie.poster_path).into(binding.recommendedMovieImage)
            }
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecommendedAdapter.RecommendedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : RecomendedItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.recomended_item, parent, false)
        return RecommendedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedAdapter.RecommendedViewHolder, position: Int){
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


}