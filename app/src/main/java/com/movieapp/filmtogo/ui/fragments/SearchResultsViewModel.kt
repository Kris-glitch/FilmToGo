package com.movieapp.filmtogo.ui.fragments

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchResultsViewModel(val app: Application, private val repository: Repository) : AndroidViewModel(app) {

    private val _movieByNameResponse = MutableLiveData<List<Movie>?>()
    private var movieSearch = ""

    fun searchMovieByName (query: String, page: Int)  : LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByName(query, page)
                if (result != null) {
                    val currentList = _movieByNameResponse.value ?: emptyList()
                    val updatedList = if (query == movieSearch) {
                        currentList + result
                    } else {
                        result
                    }
                    movieSearch = query
                    _movieByNameResponse.postValue(updatedList)
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByName: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByName: Coroutine failure", e)
            }
        }
        return _movieByNameResponse
    }
}