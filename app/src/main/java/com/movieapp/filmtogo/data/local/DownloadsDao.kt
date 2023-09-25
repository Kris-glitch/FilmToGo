package com.movieapp.filmtogo.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.movieapp.filmtogo.modelLocal.LocalMovies

@Dao
interface DownloadsDao {
    @Insert
    suspend fun insertMovie(movie : LocalMovies) : Long

    @Delete
    suspend fun deleteMovie(movie : LocalMovies)

    @Query ("DELETE FROM Movies")
    suspend fun deleteAll()

    @Query ("SELECT * FROM Movies")
    suspend fun getAllMovies() : ArrayList<LocalMovies>


}