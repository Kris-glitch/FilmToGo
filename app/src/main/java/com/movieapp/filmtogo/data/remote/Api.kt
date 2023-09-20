package com.movieapp.filmtogo.data.remote

import com.movieapp.filmtogo.modelRemote.GenresResponse
import com.movieapp.filmtogo.modelRemote.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    //Search for movies by name
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    //base URL = https://api.themoviedb.org
    //path = /3/search/movie
    //query parameters = ?api_key={api_key}
    @GET("/3/search/movie")
    suspend fun searchMovieByName(
        @Query("api_key") key: String?,
        @Query("query") query: String?,
        @Query("page") page: Int
    ): Response<MovieSearchResponse?>?

    //Get movie categories
    //https://api.themoviedb.org/3/genre/movie/list?api_key={api_key}
    @GET("/3/genre/movie/list")
    suspend fun searchGenres(
        @Query("api_key") key: String?,
    ): Response<GenresResponse?>?

    //Get movie by category
    //https://api.themoviedb.org/3/discover/movie?api_key={api_key}&with_genres=GENRE_ID
    @GET("/3/discover/movie")
    suspend fun searchMovieByGenre(
        @Query("api_key") key: String?,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<MovieSearchResponse?>?

    //Get popular movies
    // https://api.themoviedb.org/3/discover/movie?api_key=YOUR_API_KEY&with_genres=GENRE_IDS&sort_by=popularity.desc
    @GET("3/discover/movie")
    suspend fun searchPopularMoviesByGenres(
        @Query("api_key") key: String?,
        @Query("with_genres") genreIds: String, // Comma-separated genre IDs
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Response<MovieSearchResponse>

}