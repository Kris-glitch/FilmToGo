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
    suspend fun searchMovieByNameNextPage(query: String, currentPage: Int) : List<Movie>? {
        return withContext(Dispatchers.IO) {
            val nextPage = currentPage + 1
            val nextPageResults = searchMovieByName(query, nextPage) ?: return@withContext emptyList()
            val currentPageResults = searchMovieByName(query, currentPage) ?: emptyList()
            currentPageResults + nextPageResults
        }
    }
    suspend fun searchMovieByGenre (genreId: Int, page: Int) : List<Movie>? {
        return withContext(Dispatchers.IO) {
            searchMovieByGenre(genreId, page)
        }
    }
    suspend fun searchMovieByGenreNextPage (genreId: Int, currentPage: Int) : List<Movie>? {
        return withContext(Dispatchers.IO) {
            val nextPage = currentPage + 1
            val nextPageResults = searchMovieByGenre(genreId, nextPage) ?: return@withContext emptyList()
            val currentPageResults = searchMovieByGenre(genreId, currentPage) ?: emptyList()
            currentPageResults + nextPageResults
        }
    }
    suspend fun searchRecommended(genreIds: String, sortBy: String, page: Int) : List<Movie>? {
        return withContext(Dispatchers.IO) {
           apiClient.searchRecommended(genreIds, sortBy, page)
        }
    }

    suspend fun searchRecommendedNextPage(genreIds: String, sortBy: String, currentPage: Int) : List<Movie>? {
        return withContext(Dispatchers.IO) {
            val nextPage = currentPage + 1
            val nextPageResults = searchRecommended(genreIds, sortBy, nextPage) ?: return@withContext emptyList()
            val currentPageResults = searchRecommended(genreIds, sortBy, currentPage) ?: emptyList()
            currentPageResults + nextPageResults
        }
    }

    suspend fun searchGenres() : List<Genre>?{
       return withContext(Dispatchers.IO){
           apiClient.searchGenres()
        }
    }





}