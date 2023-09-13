package com.movieapp.filmtogo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.movieapp.filmtogo.R

import com.movieapp.filmtogo.databinding.FragmentUserSetupPreferencesBinding
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.ui.adapters.PreferencesAdapter


class UserSetuptPreferencesFragment : Fragment() {

    private lateinit var _binding : FragmentUserSetupPreferencesBinding
    private val binding get() = _binding!!

    private lateinit var adapter: PreferencesAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentUserSetupPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[UserSetuptPreferencesViewModel::class.java]
        viewModel.searchGenres()

        val recyclerView = binding.chooseCategories
        adapter = PreferencesAdapter(emptyList())

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        recyclerView.layoutManager = layoutManager

        viewModel.movieGenres.observe(viewLifecycleOwner) { genres ->
            genres?.let {
                adapter = PreferencesAdapter(it)
            }
        }

        recyclerView.adapter = adapter

        val selectedGenres = adapter.getSelectedList()

        binding.saveBtn.setOnClickListener(View.OnClickListener {
            if (selectedGenres == null || selectedGenres.size < 3) {
                Toast.makeText(requireContext(), "Please select minimum 3 genres.", Toast.LENGTH_SHORT).show()
            } else {
                val bundle = bundleOf("selectedGenres" to selectedGenres)
                it.findNavController().navigate(R.id.action_userSetuptPreferencesFragment_to_homepageFragment, bundle)

            }
        })
    }
}

