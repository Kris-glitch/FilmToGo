package com.movieapp.filmtogo.ui.fragments

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepageViewModel (val app: Application, private val repository: Repository) : AndroidViewModel(app)  {


    private val _movieByNameResponse = MutableLiveData<List<Movie>?>()
    var movieByNameResponse : LiveData<List<Movie>?> = MutableLiveData()

    private val _movieByNameResponseNextPage = MutableLiveData<List<Movie>?>()
    var movieByNameResponseNextPage : LiveData<List<Movie>?> = MutableLiveData()

    private val _movieByGenreResponse = MutableLiveData<List<Movie>?>()
    var movieByGenreResponse : LiveData<List<Movie>?> = MutableLiveData()

    private val _movieByGenreResponseNextPage = MutableLiveData<List<Movie>?>()
    var movieByGenreResponseNextPage : LiveData<List<Movie>?> = MutableLiveData()

    private val _movieRecommended = MutableLiveData<List<Movie>?>()
    var movieRecommended : LiveData<List<Movie>?> = MutableLiveData()

    private val _movieRecommendedNextPage = MutableLiveData<List<Movie>?>()
    var movieRecommendedNextPage : LiveData<List<Movie>?> = MutableLiveData()

    private val _movieGenres = MutableLiveData<List<Genre>?>()
    var movieGenres : LiveData<List<Genre>?> = MutableLiveData()


    suspend fun searchMovieByName (query: String, page: Int)  :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByName(query, page)
                if (result != null) {
                    _movieByNameResponse.postValue(result)
                    movieByNameResponse = _movieByNameResponse
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByName: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByName: Coroutine failure", e)
            }
        }
        return movieByNameResponse
    }
    suspend fun searchMovieByNameNextPage (query: String, page: Int)  :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByNameNextPage(query, page)
                if (result != null) {
                    _movieByNameResponseNextPage.postValue(result)
                    movieByNameResponseNextPage = _movieByNameResponseNextPage
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByNameNextPage: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByNameNextPage: Coroutine failure", e)
            }
        }
        return movieByNameResponseNextPage
    }
    suspend fun searchMovieByGenre (genreId: Int, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByGenre(genreId, page)
                if (result != null) {
                    _movieByGenreResponse.postValue(result)
                    movieByGenreResponse = _movieByGenreResponse
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByGenre: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByGenre: Coroutine failure", e)
            }
        }
        return movieByGenreResponse
    }
    suspend fun searchMovieByGenreNextPage (genreId: Int, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByGenreNextPage(genreId, page)
                if (result != null) {
                    _movieByGenreResponseNextPage.postValue(result)
                    movieByGenreResponseNextPage = _movieByGenreResponseNextPage
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByGenreNextPage: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByGenreNextPage: Coroutine failure", e)
            }
        }
        return movieByGenreResponseNextPage
    }

    suspend fun searchRecommended(genreIds: String, sortBy: String, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchRecommended(genreIds, sortBy, page)
                if (result != null) {
                    _movieRecommended.postValue(result)
                    movieRecommended = _movieRecommended
                } else {
                    Log.d(ContentValues.TAG, "searchRecommended: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchRecommended: Coroutine failure", e)
            }
        }
        return movieRecommended
    }

    fun searchRecommendedNextPage(genreIds: String, sortBy: String, page: Int) : LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchRecommendedNextPage(genreIds, sortBy, page)
                if (result != null) {
                    _movieRecommendedNextPage.postValue(result)
                    movieRecommendedNextPage = _movieRecommendedNextPage
                } else {
                    Log.d(ContentValues.TAG, "searchRecommendedNextPage: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchRecommendedNextPage: Coroutine failure", e)
            }
        }
        return movieRecommendedNextPage
    }

    fun searchGenres(): LiveData<List<Genre>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchGenres()

                if (result != null) {
                    _movieGenres.postValue(result)
                    movieGenres = _movieGenres
                } else {
                    Log.d(ContentValues.TAG, "searchGenres: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchGenres: Coroutine failure", e)
            }
        }
        return movieGenres
    }
}