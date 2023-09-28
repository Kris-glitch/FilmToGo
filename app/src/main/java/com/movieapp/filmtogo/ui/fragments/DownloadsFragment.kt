package com.movieapp.filmtogo.ui.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.databinding.FragmentDownloadsBinding
import com.movieapp.filmtogo.modelLocal.LocalMovies
import com.movieapp.filmtogo.ui.SwipeHandler
import com.movieapp.filmtogo.ui.activities.MainActivity
import com.movieapp.filmtogo.ui.adapters.DownloadsAdapter


class DownloadsFragment : Fragment() {

    private lateinit var _binding : FragmentDownloadsBinding
    private val binding get() = _binding

    private lateinit var navController : NavController
    private lateinit var downloadsViewModel : DownloadsViewModel
    private lateinit var downloadedMovies : LiveData<ArrayList<LocalMovies>?>
    private lateinit var downloadsAdapter : DownloadsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        downloadsViewModel = (activity as MainActivity).downloadsViewModel

        downloadedMovies = downloadsViewModel.getAllDownloadedMovies()

        val downRecyclerView = binding.downloadsRecyclerView
        downRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        downloadsAdapter = DownloadsAdapter()

        downloadedMovies.observe(viewLifecycleOwner){movies->
            movies?.let{
                downloadsAdapter.updateDataset(it)
                Log.d(ContentValues.TAG, "downloadedMovies list size is: " + it.size)
            }

        }

        val swipeHandler = object : SwipeHandler(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction){
                    ItemTouchHelper.LEFT -> {
                        downloadsViewModel.deleteMovie(downloadedMovies.value!![viewHolder.bindingAdapterPosition])
                        downloadsAdapter.delete(viewHolder.bindingAdapterPosition)
                        downloadsAdapter.notifyDataSetChanged()
                    }

                    ItemTouchHelper.RIGHT -> {
                        navController.navigate(DownloadsFragmentDirections.actionDownloadsFragmentToMoviePlayFragment())
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeHandler)
        touchHelper.attachToRecyclerView(downRecyclerView)

        downRecyclerView.adapter = downloadsAdapter

        binding.backBtn.setOnClickListener {
            navController.navigateUp()
        }

        binding.deleteAllBtn.setOnClickListener{
            downloadsViewModel.deleteAll()
            downloadsAdapter.notifyDataSetChanged()
        }

    }



}