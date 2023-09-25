package com.movieapp.filmtogo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movieapp.filmtogo.modelLocal.LocalMovies

@Database (entities = [LocalMovies::class], version = 1)
abstract class DownloadsDatabase : RoomDatabase(){

    abstract fun getDownloadsDao() : DownloadsDao
    companion object {

        @Volatile
        private var instance : DownloadsDatabase ?= null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,DownloadsDatabase::class.java, "Downloads DB").build()

    }
}