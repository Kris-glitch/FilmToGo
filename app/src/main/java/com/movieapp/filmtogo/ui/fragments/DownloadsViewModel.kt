package com.movieapp.filmtogo.ui.fragments

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movieapp.filmtogo.data.Repository
import com.movieapp.filmtogo.modelLocal.LocalMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DownloadsViewModel(val app: Application, private val repository: Repository) : AndroidViewModel(app) {

    private val _downloadedMovies = MutableLiveData<ArrayList<LocalMovies>?>()


    fun insertMovie (movie: LocalMovies){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertMovie(movie)
                Log.d(ContentValues.TAG, "insert movie Success")
            } catch (e : Exception){
                Log.d(ContentValues.TAG, "insert movie Failed", e)
            }
        }
    }

    fun deleteMovie (movie: LocalMovies){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteMovie(movie)
                Log.d(ContentValues.TAG, "delete movie Success")
            } catch (e : Exception){
                Log.d(ContentValues.TAG, "delete movie Failed", e)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteAll()
                Log.d(ContentValues.TAG, "delete movies Success")
            } catch (e : Exception){
                Log.d(ContentValues.TAG, "delete movies Failed", e)
            }
        }
    }

    fun getAllDownloadedMovies() : LiveData<ArrayList<LocalMovies>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getAllDownloadedMovies()
                _downloadedMovies.postValue(result)
                Log.d(ContentValues.TAG, "getAllDownloadedMovies:  Success")
            } catch (e : Exception){
                Log.d(ContentValues.TAG, "getAllDownloadedMovies: Failed", e)
            }
        }
        return _downloadedMovies
    }
}