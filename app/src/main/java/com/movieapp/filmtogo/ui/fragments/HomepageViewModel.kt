package com.movieapp.filmtogo.ui.fragments

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movieapp.filmtogo.data.ProvideStorage
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomepageViewModel (val app: Application, private val repository: Repository) : AndroidViewModel(app)  {

    private val _movieByGenreResponse = MutableLiveData<List<Movie>?>()

    private val _movieRecommended = MutableLiveData<List<Movie>?>()

    private val _movieGenres = MutableLiveData<List<Genre>?>()

    private val storage = ProvideStorage()

    private var _currentGenreId = 28

    private val _messageLiveData = MutableLiveData<String?>()
    val messageLiveData : LiveData<String?> get() = _messageLiveData


    fun searchMovieByGenre(genreId: Int, page: Int): LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByGenre(genreId, page)
                val currentList = _movieByGenreResponse.value ?: emptyList()

                val updatedList = if (genreId == _currentGenreId) {
                    currentList + result!!
                } else {
                    result!!
                }
                _currentGenreId = genreId
                _movieByGenreResponse.postValue(updatedList)

            } catch (e: IllegalStateException) {
                Log.d(ContentValues.TAG, "searchMovieByGenre: Result is null - No network connection", e)
            }
        }
        return _movieByGenreResponse
    }

    fun searchRecommended(genreIds: String, sortBy: String, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchRecommended(genreIds, sortBy, page)

                val currentList =_movieRecommended.value ?: emptyList()
                val updatedList = currentList + result!!
                _movieRecommended.postValue(updatedList)
                _messageLiveData.postValue(null)

            } catch (e: IllegalStateException) {
                Log.d(ContentValues.TAG, "searchRecommended: Result is null - No network connection", e)
                _messageLiveData.postValue("No network connection")
            }
        }
        return _movieRecommended
    }


    fun searchGenres(): LiveData<List<Genre>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchGenres()
                _movieGenres.postValue(result)

            } catch (e: IllegalStateException) {
                Log.d(ContentValues.TAG, "searchGenres: Result is null - No network connection", e)

            }
        }
        return _movieGenres
    }

    fun getPreferredGenres() : List<Genre> {
        var genres = emptyList<Genre>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                genres = storage.getPreferredGenres()!!
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchGenres: failure", e)
            }
        }
        return genres
    }
}