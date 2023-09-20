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


    private val _movieByNameResponse = MutableLiveData<List<Movie>?>()

    private val _movieByGenreResponse = MutableLiveData<List<Movie>?>()

    private val _movieRecommended = MutableLiveData<List<Movie>?>()

    private val _movieGenres = MutableLiveData<List<Genre>?>()

    private val storage = ProvideStorage()


    fun searchMovieByName (query: String, page: Int)  :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByName(query, page)
                if (result != null) {
                    val currentList = _movieByNameResponse.value ?: emptyList()
                    val updatedList = currentList + result
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

    fun searchMovieByGenre(genreId: Int, page: Int) : LiveData<List<Movie>?>{
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByGenre(genreId, page)
                if (result != null) {
                    val currentList = _movieByGenreResponse.value ?: emptyList()
                    val updatedList = currentList + result
                    _movieByGenreResponse.postValue(updatedList)
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByGenre: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByGenre: Coroutine failure", e)
            }
        }
        return _movieByGenreResponse
    }

    fun searchRecommended(genreIds: String, sortBy: String, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchRecommended(genreIds, sortBy, page)
                if (result != null) {
                    val currentList =_movieRecommended.value ?: emptyList()
                    val updatedList = currentList + result
                    _movieRecommended.postValue(updatedList)
                } else {
                    Log.d(ContentValues.TAG, "searchRecommended: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchRecommended: Coroutine failure", e)
            }
        }
        return _movieRecommended
    }


    fun searchGenres(): LiveData<List<Genre>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchGenres()

                if (result != null) {
                    _movieGenres.postValue(result)
                } else {
                    Log.d(ContentValues.TAG, "searchGenres: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchGenres: Coroutine failure", e)
            }
        }
        return _movieGenres
    }

    fun getPreferredGenres() : List<Genre>? {
        var genres = emptyList<Genre>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                genres = storage.getPreferredGenres()!!
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchGenres: Coroutine failure", e)
            }
        }
        return genres
    }
}