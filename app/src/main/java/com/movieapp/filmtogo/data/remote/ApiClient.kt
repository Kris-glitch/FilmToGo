package com.movieapp.filmtogo.data.remote

import android.content.ContentValues
import android.util.Log
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiClient {

    private val apiKey = Credentials.API_KEY

    private val api: Api = MyRetrofit.getMovieApi()

    suspend fun searchMovieByName(query: String, page: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            val response = api.searchMovieByName(apiKey, query, page)

            if (!response!!.isSuccessful) {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Log.d(ContentValues.TAG, "searchMovieByName (: $error")
                throw IllegalStateException("searchMovieByName (: $error")
            }

            val body = response.body()
            if (body == null) {
                Log.d(ContentValues.TAG, "searchMovieByName: Response error - body is null")
                throw IllegalStateException("searchMovieByName: Response error - body is null")
            }

            val results = body.results

            if (results.isEmpty()) {
                return@withContext emptyList()
            }

            results
        }
    }

    suspend fun searchMovieByGenre(genreId: Int, page: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            val response = api.searchMovieByGenre(apiKey, genreId, page)

            if (!response!!.isSuccessful) {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Log.d(ContentValues.TAG, "searchMovieByGenre (: $error")
                throw IllegalStateException("searchMovieByGenre (: $error")
            }

            val body = response.body()
            if (body == null) {
                Log.d(ContentValues.TAG, "searchMovieByGenre: Response error - body is null")
                throw IllegalStateException("searchMovieByGenre: Response error - body is null")
            }

            body.results
        }
    }

    suspend fun searchRecommended(genreIds: String, sortBy: String, page: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            val response = api.searchPopularMoviesByGenres(apiKey, genreIds, sortBy, page)

            if (!response.isSuccessful) {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Log.d(ContentValues.TAG, "searchRecommended (: $error")
                throw IllegalStateException("searchRecommended (: $error")
            }

            val body = response.body()
            if (body == null) {
                Log.d(ContentValues.TAG, "searchRecommended: Response error - body is null")
                throw IllegalStateException("searchRecommended: Response error - body is null")
            }

            body.results

        }
    }

    suspend fun searchGenres(): List<Genre> {
        return withContext(Dispatchers.IO) {
            val response = api.searchGenres(apiKey)

            if (!response!!.isSuccessful) {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Log.d(ContentValues.TAG, "searchGenres: $error")
                throw IllegalStateException("searchGenres: $error")
            }

            val body = response.body()
            if (body == null) {
                Log.d(ContentValues.TAG, "searchGenres: Response error - body is null")
                throw IllegalStateException("searchGenres: Response error - body is null")
            }

            body.genres
        }
    }


}