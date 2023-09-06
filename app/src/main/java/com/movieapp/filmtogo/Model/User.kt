package com.movieapp.filmtogo.Model

class User (val user_id : String = "",
            val username : String = "",
            val password : String = "",
            val email : String = "",
            val subscription : String = "",
            val imageURL : String = "") {

    companion object {
        var instance: User? = null
            get() {
                if (field == null) {
                    field = User()
                }
                return field
            }
            private set
    }
}