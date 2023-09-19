package com.movieapp.filmtogo.modelRemote


import java.util.Objects


class Genre (val id: Int, val name: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Genre) return false
        return id == other.id && name == other.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }
}