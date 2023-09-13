package com.movieapp.filmtogo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.movieapp.filmtogo.databinding.FragmentHomepageBinding


class HomepageFragment : Fragment() {

    private lateinit var _binding : FragmentHomepageBinding
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root

        val selectedGenres = requireArguments().getBundle("selectedGenres")
    }



}