package com.movieapp.filmtogo.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.data.local.DownloadsDatabase
import com.movieapp.filmtogo.ui.fragments.DownloadsViewModel
import com.movieapp.filmtogo.ui.fragments.DownloadsViewModelFactory
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
    lateinit var downloadsViewModel : DownloadsViewModel
    lateinit var navController : NavController

    public override fun onStart() {
        super.onStart()
        val currentUser = provideUser.currentUser

        val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val setupDone = sharedPreferences.getBoolean("setupDone", false)

        val fragmentContainer = findViewById<FragmentContainerView>(R.id.fragmentContainer)

        if (currentUser == null) {
            goToLogin()
        }

        if (!setupDone){
            navController = fragmentContainer.findNavController()
            navController.navigate(R.id.userSetupWelcomeFragment)
        } else {
            navController.navigate(R.id.homepageFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        val currentUser = provideUser.currentUser

        val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val setupDone = sharedPreferences.getBoolean("setupDone", false)

        val fragmentContainer = findViewById<FragmentContainerView>(R.id.fragmentContainer)

        if (currentUser == null) {
            goToLogin()
        }

        if (!setupDone){
            navController = fragmentContainer.findNavController()
            navController.navigate(R.id.userSetupWelcomeFragment)
        } else {
            navController.navigate(R.id.homepageFragment)
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

        val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)

        val setupDone = sharedPreferences.getBoolean("setupDone", true)

        val fragmentContainer = findViewById<FragmentContainerView>(R.id.fragmentContainer)
        navController = fragmentContainer.findNavController()

        if (isLoggedIn && setupDone) {
            navController.navigate(R.id.homepageFragment)
        } else if (shouldSetupProfile) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("setupDone", false);
            editor.apply();

            navController.navigate(R.id.userSetupWelcomeFragment)
        }

        val repository = Repository(DownloadsDatabase(this))

        val viewModelProviderFactory = PreferencesViewModelFactory(application, repository)
        preferencesViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserSetuptPreferencesViewModel::class.java)

        val homepageViewModelFactory = HomepageViewModelFactory(application, repository)
        homepageViewModel = ViewModelProvider(this, homepageViewModelFactory).get(HomepageViewModel::class.java)

        val searchResultsViewModelFactory = SearchResultsViewModelFactory(application, repository)
        searchResultsViewModel = ViewModelProvider(this, searchResultsViewModelFactory).get(SearchResultsViewModel::class.java)

        val downloadsViewModelFactory = DownloadsViewModelFactory (application, repository)
        downloadsViewModel = ViewModelProvider(this, downloadsViewModelFactory).get(DownloadsViewModel::class.java)
    }


}