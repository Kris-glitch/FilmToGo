package com.movieapp.filmtogo.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.FragmentSearchResultsBinding
import com.movieapp.filmtogo.modelRemote.Movie
import com.movieapp.filmtogo.ui.activities.MainActivity
import com.movieapp.filmtogo.ui.adapters.SearchResultsAdapter


class SearchResultsFragment : Fragment() {


    private lateinit var _binding : FragmentSearchResultsBinding

    private val binding get() = _binding
    private var selectedMovie: Movie? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (activity as MainActivity).searchResultsViewModel

        val navController = Navigation.findNavController(view)

        val args: SearchResultsFragmentArgs by navArgs()
        val searchQuery = args.searchQuery
        var currentPageByName = 1

        val searchedMovies = viewModel.searchMovieByName(searchQuery, currentPageByName)

        val noNetworkLayout = layoutInflater.inflate(R.layout.no_network, binding.containerLayout, false)

        viewModel.messageLiveData.observe(viewLifecycleOwner) { message ->
            if (message == "No results of this search") {
                binding.noResults.visibility = View.VISIBLE
            } else if (message == "No network connection") {
                binding.containerLayout.addView(noNetworkLayout)
            } else {
                binding.noResults.visibility = View.GONE
                binding.containerLayout.removeView(noNetworkLayout)
            }
        }

        val searchRecyclerView = binding.searchRecyclerView
        searchRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)

        val searchResultsAdapter = SearchResultsAdapter() { clickedMovie ->
            selectedMovie = clickedMovie
            goToMovieDetails(navController, selectedMovie!!)
        }

        searchedMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                searchResultsAdapter.updateDataset(it)
            }
        }

        searchRecyclerView.adapter = searchResultsAdapter

        searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    currentPageByName++
                    viewModel.searchMovieByName(searchQuery, currentPageByName)
                }
            }

        })

        binding.backBtn.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun goToMovieDetails(navController: NavController, movie: Movie){
        navController.navigate(SearchResultsFragmentDirections.actionSearchResultsFragmentToMovieDetailFragment(movie))

    }

}