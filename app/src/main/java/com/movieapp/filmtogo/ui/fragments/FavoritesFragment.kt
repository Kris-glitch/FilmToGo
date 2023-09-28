package com.movieapp.filmtogo.ui.fragments


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.data.ProvideStorage
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.databinding.FragmentFavoritesBinding
import com.movieapp.filmtogo.modelRemote.Movie
import com.movieapp.filmtogo.ui.SwipeHandler
import com.movieapp.filmtogo.ui.adapters.FavouritesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoritesFragment : Fragment() {

    private lateinit var _binding : FragmentFavoritesBinding
    private val binding get() = _binding

    private lateinit var navController : NavController
    private lateinit var favouriteMovies : LiveData<ArrayList<Movie>>
    private lateinit var favouritesAdapter : FavouritesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val provideUser = ProvideUser()
        val provideStorage = ProvideStorage()

        val userId = provideUser.getCurrentUserId()

        val shareMovies: ArrayList<Movie> = ArrayList()

        favouriteMovies = provideStorage.getFavoriteMoviesForUser(userId)


        val favouritesRecyclerView = binding.favouritesRecyclerView
        favouritesRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        favouritesAdapter = FavouritesAdapter()
        favouriteMovies.observe(viewLifecycleOwner){
            favouritesAdapter.updateDataset(it)
            shareMovies.clear()
            shareMovies.addAll(it)
            Log.d(ContentValues.TAG, "favourites list size is: " + it.size)
        }


        val swipeHandler = object : SwipeHandler(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction){
                    ItemTouchHelper.LEFT -> {
                        val movieDel = favouriteMovies.value!![viewHolder.bindingAdapterPosition]
                        lifecycleScope.launch(Dispatchers.IO) {
                            provideStorage.deleteFavoriteMovie(userId, movieDel.id.toString())
                        }
                        favouritesAdapter.delete(viewHolder.bindingAdapterPosition)
                        favouritesAdapter.notifyDataSetChanged()
                    }

                    ItemTouchHelper.RIGHT -> {
                        navController.navigate(FavoritesFragmentDirections.actionFavouritesFragmentToMoviePlayFragment())
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeHandler)
        touchHelper.attachToRecyclerView(favouritesRecyclerView)

        favouritesRecyclerView.adapter = favouritesAdapter

        binding.backBtn.setOnClickListener {
            navController.navigateUp()
        }
        binding.shareBtn.setOnClickListener {
            val message = buildMessageFromMovies(shareMovies)
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }

            startActivity(Intent.createChooser(sendIntent, "Share via"))

        }



    }
    private fun buildMessageFromMovies(movies: ArrayList<Movie>): String {
        val builder = StringBuilder()

        builder.append("My Favorite Movies:\n\n")

        for ((index, movie) in movies.withIndex()) {
            val movieNumber = index + 1
            builder.append("$movieNumber. ${movie.title}\n")
        }

        return builder.toString()
    }

}