package com.movieapp.filmtogo.modelRemote

class Movie (val genre_ids: List<Int>,
             val id: Int,
             val original_language: String,
             val overview: String,
             val popularity: Double,
             val poster_path: String?,
             val release_date: String,
             val title: String,
             val vote_average: Double){
}