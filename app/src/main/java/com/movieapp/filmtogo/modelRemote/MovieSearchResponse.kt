package com.movieapp.filmtogo.modelRemote

class MovieSearchResponse (
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
){
}