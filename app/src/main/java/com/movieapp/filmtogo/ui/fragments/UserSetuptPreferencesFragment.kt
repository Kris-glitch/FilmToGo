package com.movieapp.filmtogo.ui.fragments

import com.movieapp.filmtogo.ui.adapters.PreferencesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.remote.ProvideStorage
import com.movieapp.filmtogo.databinding.FragmentUserSetupPreferencesBinding
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.ui.activities.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserSetuptPreferencesFragment : Fragment() {

    private lateinit var _binding : FragmentUserSetupPreferencesBinding
    private val binding get() = _binding

    private lateinit var preferencesAdapter: PreferencesAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentUserSetupPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val provideStorage = ProvideStorage()

        val viewModel = (activity as MainActivity).preferencesViewModel
        val movieGenres : LiveData<List<Genre>?> = viewModel.searchGenres()

        val recyclerView = binding.chooseCategories

        preferencesAdapter = PreferencesAdapter()

        recyclerView.adapter = preferencesAdapter

        movieGenres.observe(viewLifecycleOwner) { genres ->
            genres?.let {
                preferencesAdapter.updateDataset(it)
            }
        }


        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        recyclerView.layoutManager = layoutManager

        val selectedGenres = preferencesAdapter.getSelectedList()

        binding.saveBtn.setOnClickListener(View.OnClickListener {
            if (selectedGenres == null || selectedGenres.size < 3) {
                Toast.makeText(requireContext(), "Please select minimum 3 genres.", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    lifecycleScope.launch(Dispatchers.IO){
                        provideStorage.saveDataForRecommendation(selectedGenres)
                        withContext(Dispatchers.Main) {
                            it.findNavController().navigate(R.id.action_userSetuptPreferencesFragment_to_homepageFragment)
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireView().context,"Something went wrong, check your connection",Toast.LENGTH_LONG).show()
                }

            }
        })
    }


}


