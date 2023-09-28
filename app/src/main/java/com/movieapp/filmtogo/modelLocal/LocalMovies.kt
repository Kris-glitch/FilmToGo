package com.movieapp.filmtogo.modelLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Movies")
data class LocalMovies(@PrimaryKey (autoGenerate = true) val id: Int?,
                       val poster_path: String?,
                       val release_date: String,
                       val title: String,
                       val vote_average: Double){

}