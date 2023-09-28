package com.movieapp.filmtogo.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.movieapp.filmtogo.modelLocal.LocalMovies

@Dao
interface DownloadsDao {
    @Insert
    fun insertMovie(movie : LocalMovies) : Long

    @Delete
    fun deleteMovie(movie : LocalMovies)

    @Query ("DELETE FROM Movies")
    fun deleteAll()

    @Query ("SELECT * FROM Movies")
    fun getAllMovies() : List<LocalMovies>


}