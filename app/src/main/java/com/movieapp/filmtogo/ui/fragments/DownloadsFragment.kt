package com.movieapp.filmtogo.ui.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.filmtogo.databinding.FragmentDownloadsBinding
import com.movieapp.filmtogo.ui.activities.MainActivity
import com.movieapp.filmtogo.ui.adapters.DownloadsAdapter


class DownloadsFragment : Fragment() {

    private lateinit var _binding : FragmentDownloadsBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        val downloadsViewModel = (activity as MainActivity).downloadsViewModel

        val downloadedMovies = downloadsViewModel.getAllDownloadedMovies()

        val downRecyclerView = binding.downloadsRecyclerView
        downRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        val downloadsAdapter = DownloadsAdapter()

        downloadedMovies.observe(viewLifecycleOwner){movies->
            movies?.let{
                downloadsAdapter.updateDataset(it)
            }

        }

        val swipeGesture = object : SwipeGesture(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction){
                    ItemTouchHelper.LEFT -> {
                        downloadsAdapter.deleteItem(viewHolder.bindingAdapterPosition)
                    }

                    ItemTouchHelper.RIGHT -> {
                        navController.navigate(DownloadsFragmentDirections.actionDownloadsFragmentToMoviePlayFragment())
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(downRecyclerView)

        downRecyclerView.adapter = downloadsAdapter

    }

}