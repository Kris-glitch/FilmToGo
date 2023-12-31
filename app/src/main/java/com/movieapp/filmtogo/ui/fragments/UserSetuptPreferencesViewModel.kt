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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserSetuptPreferencesViewModel(val app: Application, private val repository: Repository) : AndroidViewModel(app){

    private val _movieGenres = MutableLiveData<List<Genre>?>()


    fun searchGenres(): LiveData<List<Genre>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchGenres()

                if (result != null) {
                    _movieGenres.postValue(result)
                    _movieGenres.value?.let { genres ->
                        Log.d(ContentValues.TAG, "searchGenres from ViewModel: $genres")
                    }
                } else {
                    Log.d(ContentValues.TAG, "searchGenres from ViewModel: Response error - null")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "searchGenres from ViewModel: Coroutine failure", e)
            }
        }
        return _movieGenres
    }

}