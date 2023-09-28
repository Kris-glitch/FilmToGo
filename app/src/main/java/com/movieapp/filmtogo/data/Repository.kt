package com.movieapp.filmtogo.data


import com.movieapp.filmtogo.data.local.DownloadsDao
import com.movieapp.filmtogo.data.local.DownloadsDatabase
import com.movieapp.filmtogo.data.remote.ApiClient
import com.movieapp.filmtogo.modelLocal.LocalMovies
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(downloadsDatabase: DownloadsDatabase){

    private var downloadsDao: DownloadsDao? = downloadsDatabase.getDownloadsDao()

    //Retrofit
    companion object {
        val apiClient : ApiClient by lazy { ApiClient() }
    }

    suspend fun searchMovieByName (query: String, page: Int) : List<Movie>{
        return withContext(Dispatchers.IO) {
            apiClient.searchMovieByName(query, page)
        }
    }
    suspend fun searchMovieByGenre (genreId: Int, page: Int) : List<Movie> {
        return withContext(Dispatchers.IO) {
            apiClient.searchMovieByGenre(genreId, page)
        }
    }
    suspend fun searchRecommended(genreIds: String, sortBy: String, page: Int) : List<Movie> {
        return withContext(Dispatchers.IO) {
           apiClient.searchRecommended(genreIds, sortBy, page)
        }
    }


    suspend fun searchGenres() : List<Genre> {
       return withContext(Dispatchers.IO){
           apiClient.searchGenres()
        }
    }

    //Room
    suspend fun insertMovie (movie : LocalMovies) {
        return withContext(Dispatchers.IO){
            downloadsDao?.insertMovie(movie)
        }
    }
    suspend fun deleteMovie (movie : LocalMovies) {
        return withContext(Dispatchers.IO) {
            downloadsDao?.deleteMovie(movie)
        }
    }
    suspend fun deleteAll () {
        return withContext(Dispatchers.IO) {
            downloadsDao?.deleteAll()
        }
    }
    suspend fun getAllDownloadedMovies(): ArrayList<LocalMovies> {
        return withContext(Dispatchers.IO) {
            val list: List<LocalMovies> = downloadsDao?.getAllMovies() ?: emptyList()
            return@withContext ArrayList(list)
        }
    }

}