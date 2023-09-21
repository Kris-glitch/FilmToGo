package com.movieapp.filmtogo.ui.fragments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movieapp.filmtogo.data.Repository

class SearchResultsViewModelFactory(val app: Application, private val repository: Repository) : ViewModelProvider.Factory{
    override fun <T: ViewModel>create (modelClass: Class<T>):T{
        return SearchResultsViewModel (app, repository) as T
    }
}