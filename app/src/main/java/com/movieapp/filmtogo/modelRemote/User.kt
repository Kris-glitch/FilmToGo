package com.movieapp.filmtogo.modelRemote

class User (val user_id : String = "",
            var username : String = "",
            var email : String = "",
            var subscription : String = "") {

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