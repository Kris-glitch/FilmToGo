package com.movieapp.filmtogo.ui.fragments

import android.app.Application
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.Repository

import com.movieapp.filmtogo.databinding.FragmentUserSetupPreferencesBinding
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.ui.activities.MainActivity
import com.movieapp.filmtogo.ui.adapters.PreferencesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class UserSetuptPreferencesFragment : Fragment() {

    private lateinit var _binding : FragmentUserSetupPreferencesBinding
    private val binding get() = _binding!!

    private lateinit var adapter: PreferencesAdapter

    private val recommendationsDatabase = Firebase.database.getReference("Recommendations")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentUserSetupPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (activity as MainActivity).preferencesViewModel
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
                lifecycleScope.launch(Dispatchers.IO){
                    saveDataForRecommendation(selectedGenres)
                }
                it.findNavController().navigate(R.id.action_userSetuptPreferencesFragment_to_homepageFragment)

            }
        })
    }

    private suspend fun saveDataForRecommendation(selectedGenres : List<Genre>) {
        try {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val userID = currentUser.uid
                val hashMap : HashMap<String, List<Genre>> = hashMapOf("cardNumber" to selectedGenres)

                val databaseRef = recommendationsDatabase.child(userID)

                databaseRef.setValue(hashMap).await()

                Log.d(ContentValues.TAG, "saveDataForRecommendation:success")
            }
        } catch (e: Exception) {
            requireActivity().runOnUiThread {
                Toast.makeText(requireView().context,"Something went wrong. Please try again later.",Toast.LENGTH_LONG).show()
                Log.w(ContentValues.TAG, "saveDataForRecommendation:failure", e)
            }
        }
    }
}


