package com.movieapp.filmtogo.data

import com.movieapp.filmtogo.data.remote.ApiClient
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    companion object {
        val apiClient : ApiClient by lazy { ApiClient() }
    }

    suspend fun searchMovieByName (query: String, page: Int) : List<Movie>?{
        return withContext(Dispatchers.IO) {
            apiClient.searchMovieByName(query, page)
        }
    }
    suspend fun searchMovieByGenre (genreId: Int, page: Int) : List<Movie>? {
        return withContext(Dispatchers.IO) {
            apiClient.searchMovieByGenre(genreId, page)
        }
    }
    suspend fun searchRecommended(genreIds: String, sortBy: String, page: Int) : List<Movie>? {
        return withContext(Dispatchers.IO) {
           apiClient.searchRecommended(genreIds, sortBy, page)
        }
    }


    suspend fun searchGenres() : List<Genre>?{
       return withContext(Dispatchers.IO){
           apiClient.searchGenres()
        }
    }





}