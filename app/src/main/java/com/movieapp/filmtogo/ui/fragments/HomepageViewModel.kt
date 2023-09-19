package com.movieapp.filmtogo.ui.fragments

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomepageViewModel (val app: Application, private val repository: Repository) : AndroidViewModel(app)  {


    private val _movieByNameResponse = MutableLiveData<List<Movie>?>()

    private val _movieByNameResponseNextPage = MutableLiveData<List<Movie>?>()

    private val _movieByGenreResponse = MutableLiveData<List<Movie>?>()

    private val _movieByGenreResponseNextPage = MutableLiveData<List<Movie>?>()

    private val _movieRecommended = MutableLiveData<List<Movie>?>()

    private val _movieRecommendedNextPage = MutableLiveData<List<Movie>?>()

    private val _movieGenres = MutableLiveData<List<Genre>?>()


    fun searchMovieByName (query: String, page: Int)  :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByName(query, page)
                if (result != null) {
                    _movieByNameResponse.postValue(result)
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByName: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByName: Coroutine failure", e)
            }
        }
        return _movieByNameResponse
    }
    fun searchMovieByNameNextPage (query: String, page: Int)  :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByNameNextPage(query, page)
                if (result != null) {
                    _movieByNameResponseNextPage.postValue(result)
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByNameNextPage: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByNameNextPage: Coroutine failure", e)
            }
        }
        return _movieByNameResponseNextPage
    }
    fun searchMovieByGenre (genreId: Int, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByGenre(genreId, page)
                if (result != null) {
                    _movieByGenreResponse.postValue(result)
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByGenre: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByGenre: Coroutine failure", e)
            }
        }
        return _movieByGenreResponse
    }
    fun searchMovieByGenreNextPage (genreId: Int, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchMovieByGenreNextPage(genreId, page)
                if (result != null) {
                    _movieByGenreResponseNextPage.postValue(result)
                } else {
                    Log.d(ContentValues.TAG, "searchMovieByGenreNextPage: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchMovieByGenreNextPage: Coroutine failure", e)
            }
        }
        return _movieByGenreResponseNextPage
    }

    fun searchRecommended(genreIds: String, sortBy: String, page: Int) :LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchRecommended(genreIds, sortBy, page)
                if (result != null) {
                    _movieRecommended.postValue(result)
                } else {
                    Log.d(ContentValues.TAG, "searchRecommended: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchRecommended: Coroutine failure", e)
            }
        }
        return _movieRecommended
    }

    fun searchRecommendedNextPage(genreIds: String, sortBy: String, page: Int) : LiveData<List<Movie>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchRecommendedNextPage(genreIds, sortBy, page)
                if (result != null) {
                    _movieRecommendedNextPage.postValue(result)
                } else {
                    Log.d(ContentValues.TAG, "searchRecommendedNextPage: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchRecommendedNextPage: Coroutine failure", e)
            }
        }
        return _movieRecommendedNextPage
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
}