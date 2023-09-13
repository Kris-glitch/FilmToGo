package com.movieapp.filmtogo.modelLocal

import android.os.Parcelable
import com.movieapp.filmtogo.modelRemote.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedGenres(val genres: List<Genre>) : Parcelable