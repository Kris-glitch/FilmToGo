package com.movieapp.filmtogo.ui.fragments
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.databinding.FragmentHomepageBinding
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import com.movieapp.filmtogo.ui.activities.MainActivity
import com.movieapp.filmtogo.ui.adapters.GenresAdapter
import com.movieapp.filmtogo.ui.adapters.MovieByGenreAdapter
import com.movieapp.filmtogo.ui.adapters.RecommendedAdapter



class HomepageFragment : Fragment() {

    private lateinit var _binding : FragmentHomepageBinding
    private val binding get() = _binding
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

        val navController = findNavController(view)

        val viewModel = (activity as MainActivity).homepageViewModel

        //No Connection

        val noNetworkLayout = layoutInflater.inflate(R.layout.no_network, binding.containerLayout, false)

        viewModel.messageLiveData.observe(viewLifecycleOwner) { message ->
            if (message == "No network connection") {
                binding.containerLayout.addView(noNetworkLayout)
            } else {
                binding.containerLayout.removeView(noNetworkLayout)
            }
        }

        var currentPageByGenre = 1
        var currentPageRecommended = 1

        //Genres Recycler view

        val movieGenres = viewModel.searchGenres()

        val genresRecyclerView = binding.genresRecyclerView
        genresRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        val defaultGenre = Genre(28,"Action")
        var selectedGenre = MutableLiveData(defaultGenre)


        val genreAdapter = GenresAdapter(){clickedGenre ->
            selectedGenre.value = clickedGenre

        }
        movieGenres.observe(viewLifecycleOwner) { genres ->
            genres?.let {
                genreAdapter.updateDataset(it)
            }
        }

        genresRecyclerView.adapter = genreAdapter

        //Movies By Genre Recycler View

        val moviesByGenreLiveData: MutableLiveData<List<Movie>> = MutableLiveData()


        selectedGenre.observe(viewLifecycleOwner) { genre ->
            viewModel.searchMovieByGenre(genre.id, currentPageByGenre).observe(viewLifecycleOwner) { movies ->
                val movieList = movies ?: emptyList()
                moviesByGenreLiveData.value = movieList
                Log.d("HomepageFragment", "Movies by genre updated: ${movieList.size} movies")

            }
        }

        val movieByGenreRecyclerView = binding.movieByGenreRecyclerView
        movieByGenreRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        val moviesByGenresAdapter = MovieByGenreAdapter(){clickedMovie ->
            selectedMovie = clickedMovie
            goToMovieDetails(navController, selectedMovie!!)
        }


        moviesByGenreLiveData.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                moviesByGenresAdapter.updateDataset(it)
                Log.d("HomepageFragment", "moviesByGenreLiveData: ${movies.size} movies")
            }
        }

        movieByGenreRecyclerView.adapter = moviesByGenresAdapter

        //Movies By Genre Recycler View next pages

        movieByGenreRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dx > 0) {
                    // Scrolling to the left (loading more movies).
                    val genre = selectedGenre.value
                    currentPageByGenre++
                    if (genre != null) {
                        viewModel.searchMovieByGenre(genre.id, currentPageByGenre)
                    }

                }
            }
        })

        //Recommended movies Recycler View

        val preferences = viewModel.getPreferredGenres()
        val preferredGenreIds = getIds(preferences)

        val recommendedMovies = viewModel.searchRecommended(preferredGenreIds,"popularity.desc",currentPageRecommended)

        val recommendedRecyclerView = binding.recommendedRecyclerView
        recommendedRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        val recommendedAdapter = RecommendedAdapter(navController){clickedMovie ->
            selectedMovie = clickedMovie
            goToMovieDetails(navController, selectedMovie!!)

        }

        recommendedMovies.observe(viewLifecycleOwner){movies->
            movies?.let{
                recommendedAdapter.updateDataset(it)
            }

        }

        recommendedRecyclerView.adapter = recommendedAdapter

        //Recommended movies next pages

        recommendedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollHorizontally(1)) {
                    currentPageRecommended++
                    viewModel.searchRecommended(preferredGenreIds,"popularity.desc",currentPageRecommended)
                }
            }

        })

        //Menu Buttons

        val bottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {

                    true
                }

                R.id.menu_search -> {
                    openSearchDialog(navController)
                    true
                }

                R.id.menu_profile -> {
                    navController.navigate(HomepageFragmentDirections.actionHomepageFragmentToProfileFragment())
                    true
                }

                R.id.menu_logout -> {
                    val provideUser = ProvideUser()
                    provideUser.logoutUser()
                    (activity as MainActivity).goToLogin()
                    true
                }

                else -> false
            }
        }
    }

    private fun openSearchDialog(navController: NavController) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.search_overlay_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val searchView = dialogView.findViewById<SearchView>(R.id.searchViewOverlay)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                navController.navigate(HomepageFragmentDirections.actionHomepageFragmentToSearchResultsFragment(query))
                dialog.dismiss()
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.requestFocus()
        dialog.show()
    }


    private fun goToMovieDetails(navController: NavController, movie: Movie){
        navController.navigate(HomepageFragmentDirections.actionHomepageFragmentToMovieDetailFragment(movie))

    }

    private fun getIds(preferences: List<Genre>?): String {
        var ids = ""
        if (preferences != null) {
            for ((index, genre) in preferences.withIndex()) {
                ids += genre.id.toString()
                if (index < preferences.size - 1) {
                    ids += ","
                }
            }
        }
        return ids
    }





}