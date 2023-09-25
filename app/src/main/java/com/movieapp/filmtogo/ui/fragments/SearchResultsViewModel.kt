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
    private val _messageLiveData = MutableLiveData<String?>()
    val messageLiveData: LiveData<String?> get() = _messageLiveData

    fun searchMovieByName(query: String, page: Int): LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByName(query, page)
                val currentList = _movieByNameResponse.value ?: emptyList()
                val updatedList = if (query == movieSearch) {
                    currentList + (result ?: emptyList())
                } else {
                    result ?: emptyList()
                }
                movieSearch = query
                _movieByNameResponse.postValue(updatedList)

                if (updatedList.isEmpty()) {
                    _messageLiveData.postValue("No results of this search")
                } else {
                    _messageLiveData.postValue(null)
                }
            } catch (e: IllegalStateException) {
                Log.d(ContentValues.TAG, "searchMovieByName: Body is null - Network failure", e)
                _messageLiveData.postValue("No network connection")
            }
        }
        return _movieByNameResponse
    }
}
