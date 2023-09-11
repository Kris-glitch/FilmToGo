package com.movieapp.filmtogo.modelLocal

import com.movieapp.filmtogo.modelRemote.User

class UserLocal (val user_local_id : String = "",
                 val username_local : String = "",
                 val password_local : String = "",
                 val email_local : String = "",
                 val subscription_local : String = "",
                 val imageURL_local : String = "",
                 val moviePreferences : ArrayList<String> = ArrayList()) {

    companion object {
        var instance: UserLocal? = null
            get() {
                if (field == null) {
                    field = UserLocal()
                }
                return field
            }
            private set
    }
}