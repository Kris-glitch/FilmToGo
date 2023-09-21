package com.movieapp.filmtogo.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.ui.fragments.HomepageViewModel
import com.movieapp.filmtogo.ui.fragments.HomepageViewModelFactory
import com.movieapp.filmtogo.ui.fragments.PreferencesViewModelFactory
import com.movieapp.filmtogo.ui.fragments.SearchResultsViewModel
import com.movieapp.filmtogo.ui.fragments.SearchResultsViewModelFactory
import com.movieapp.filmtogo.ui.fragments.UserSetuptPreferencesViewModel


class MainActivity : AppCompatActivity() {

    private val provideUser = ProvideUser()
    lateinit var preferencesViewModel : UserSetuptPreferencesViewModel
    lateinit var homepageViewModel : HomepageViewModel
    lateinit var searchResultsViewModel : SearchResultsViewModel

    public override fun onStart() {
        super.onStart()
        val currentUser = provideUser.currentUser
        if (currentUser == null) {
            goToLogin()
        }
    }

    fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

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

        val homepageViewModelFactory = HomepageViewModelFactory(application, Repository())
        homepageViewModel = ViewModelProvider(this, homepageViewModelFactory).get(HomepageViewModel::class.java)

        val searchResultsViewModelFactory = SearchResultsViewModelFactory(application, Repository())
        searchResultsViewModel = ViewModelProvider(this, searchResultsViewModelFactory).get(SearchResultsViewModel::class.java)
    }


}