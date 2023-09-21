package com.movieapp.filmtogo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.movieapp.filmtogo.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment() {

    private lateinit var _binding : FragmentMovieDetailBinding
    private val binding get() = _binding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: MovieDetailFragmentArgs by navArgs()
        val movie = args.movie

        binding.apply {
            Glide.with(binding.root.context).load("https://image.tmdb.org/t/p/original/"
                    + movie.poster_path).into(movieImage)
            movieTitle.text = movie.title
            ratingBar.rating = (movie.vote_average / 2).toFloat()
            movieYear.text = movie.release_date
            movieOriginalLanguage.text = movie.original_language
            movieDescription.text = movie.overview
            playBtn.setOnClickListener{
                view.findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMoviePlayFragment())
            }
            downloadBtn.setOnClickListener{
                val title = movie.title
                Toast.makeText(requireContext(), "$title is Downloaded", Toast.LENGTH_LONG).show()
            }
            backBtn.setOnClickListener {
                Navigation.findNavController(view).navigateUp()
            }
            favoritesIconBtn.setOnClickListener{
                val title = movie.title
                Toast.makeText(requireContext(), "$title is added to Favourites", Toast.LENGTH_LONG).show()
            }
        }


    }


}