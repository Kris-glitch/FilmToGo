package com.movieapp.filmtogo.modelRemote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Objects

@Parcelize
class Movie @JvmOverloads constructor(val genre_ids: List<Int> = emptyList(),
                                      val id: Int = 0,
                                      val original_language: String = "",
                                      val overview: String = "",
                                      val popularity: Double = 0.0,
                                      val poster_path: String? = null,
                                      val release_date: String = "",
                                      val title: String = "",
                                      val vote_average: Double = 0.0) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Movie) return false
        return id == other.id
                && original_language == other.original_language
                && overview == other.overview
                && popularity == other.popularity
                && poster_path == other.poster_path
                && release_date == other.release_date
                && title == other.title
                && vote_average == other.vote_average
    }

    override fun hashCode(): Int {
        return Objects.hash(id, original_language, overview, popularity, poster_path, release_date, title, vote_average)
    }
}