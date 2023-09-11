package com.movieapp.filmtogo.modelRemote

class User (val user_id : String = "",
            val username : String = "",
            val email : String = "",
            var subscription : String = "",
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