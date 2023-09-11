package com.movieapp.filmtogo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.movieapp.filmtogo.R


class MainActivity : AppCompatActivity() {
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
    }
}