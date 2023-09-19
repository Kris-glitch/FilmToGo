package com.movieapp.filmtogo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieapp.filmtogo.databinding.FragmentHomepageBinding
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import com.movieapp.filmtogo.ui.activities.MainActivity
import com.movieapp.filmtogo.ui.adapters.GenresAdapter
import com.movieapp.filmtogo.ui.adapters.MovieByGenreAdapter


class HomepageFragment : Fragment() {

    private lateinit var _binding : FragmentHomepageBinding
    private val binding get() = _binding
    private var selectedGenre: Genre? = null
    private var selectedMovie: Movie? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (activity as MainActivity).homepageViewModel
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



        //Genres Recycler view

        val movieGenres = viewModel.searchGenres()

        val genresRecyclerView = binding.genresRecyclerView
        genresRecyclerView.layoutManager = horizontalLayoutManager

        val genreAdapter = GenresAdapter(){clickedGenre ->
            selectedGenre = clickedGenre
        }

        movieGenres.observe(viewLifecycleOwner) { genres ->
            genres?.let {
                genreAdapter.updateDataset(it)
            }
        }

        genresRecyclerView.adapter = genreAdapter


        //Movies By Genre Recycler View

        val moviesByGenre = selectedGenre?.let { viewModel.searchMovieByGenre(it.id, 1) }

        val movieByGenreRecyclerView = binding.movieByGenreRecyclerView
        movieByGenreRecyclerView.layoutManager = horizontalLayoutManager

        val moviesByGenresAdapter = MovieByGenreAdapter(){clickedMovie ->
            selectedMovie = clickedMovie
            //todo : go to next fragment
        }

        if (moviesByGenre != null) {
            moviesByGenre.observe(viewLifecycleOwner){ movies ->
                movies?.let {
                    moviesByGenresAdapter.updateDataset(it)
                }
            }
        }





    }



}