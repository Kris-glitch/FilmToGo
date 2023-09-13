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

    suspend fun searchMovieByName(query: String, page: Int): List<Movie>? {
        return withContext(Dispatchers.IO) {
            val response = api.searchMovieByName(apiKey, query, page)

            if (response != null) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        return@withContext body.results
                    } else {
                        Log.d(ContentValues.TAG, "searchMovieByName: Response error - body is null")
                        return@withContext null
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    Log.d(ContentValues.TAG, "searchMovieByName: $error")
                    return@withContext null
                }
            } else {
                return@withContext null
            }
        }
    }

    suspend fun searchMovieByGenre(genreId: Int, page: Int): List<Movie>? {
        return withContext(Dispatchers.IO) {
            val response = api.searchMovieByGenre(apiKey, genreId, page)

            if (response != null) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        return@withContext body.results
                    } else {
                        Log.d(ContentValues.TAG, "searchMovieByGenre: Response error - body is null")
                        return@withContext null
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    Log.d(ContentValues.TAG, "searchMovieByGenre: $error")
                    return@withContext null
                }
            } else {
                return@withContext null
            }
        }
    }

    suspend fun searchRecommended(genreIds: String, sortBy: String, page: Int): List<Movie>? {
        return withContext(Dispatchers.IO) {
            val response = api.searchPopularMoviesByGenres(apiKey, genreIds, sortBy, page)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return@withContext body.results
                } else {
                    Log.d(ContentValues.TAG, "searchRecommended: Response error - body is null")
                    return@withContext null
                }
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Log.d(ContentValues.TAG, "searchRecommended: $error")
                return@withContext null
            }
        }
    }

    suspend fun searchGenres(): List<Genre>? {
        return withContext(Dispatchers.IO) {
            val response = api.searchGenres(apiKey)

            if (response != null) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        return@withContext body.genres
                    } else {
                        Log.d(ContentValues.TAG, "searchGenres: Response error - body is null")
                        return@withContext null
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    Log.d(ContentValues.TAG, "searchGenres: $error")
                    return@withContext null
                }
            } else {
                return@withContext null
            }
        }
    }


}