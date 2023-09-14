package com.movieapp.filmtogo.ui.activities

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.ui.fragments.PreferencesViewModelFactory
import com.movieapp.filmtogo.ui.fragments.UserSetuptPreferencesViewModel


class MainActivity : AppCompatActivity() {


    lateinit var preferencesViewModel : UserSetuptPreferencesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)
        val shouldSetupProfile = intent.getBooleanExtra("shouldSetupProfile", false)

        val fragmentContainer = findViewById<FragmentContainerView>(R.id.fragmentContainer)
        val navController = fragmentContainer.findNavController()

        if (isLoggedIn) {
            navController.navigate(R.id.homepageFragment)
        } else if (shouldSetupProfile) {
            navController.navigate(R.id.userSetupWelcomeFragment)
        }

        val viewModelProviderFactory = PreferencesViewModelFactory(application, Repository())
        preferencesViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserSetuptPreferencesViewModel::class.java)
    }


}