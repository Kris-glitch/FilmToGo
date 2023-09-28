package com.movieapp.filmtogo.data

import android.content.ContentValues
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.movieapp.filmtogo.modelRemote.Subscription
import com.movieapp.filmtogo.modelRemote.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await


class ProvideUser(private val userSignupCallback: UserSignupCallback? = null,
                  private val userLoginCallback: UserLoginCallback? = null) {

    companion object {
        private const val TAG = "ProvideUser"
    }


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var databaseRef: DatabaseReference
    val currentUser = auth.currentUser

    fun getCurrentUserId() : String {
        var id = ""
        if (currentUser != null){
            id = currentUser.uid
        }
        return id
    }

    suspend fun login(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
        } catch (e: Exception){
            withContext(Dispatchers.Main) {
                userLoginCallback?.onError("Something went wrong. Please try again later.")
                Log.w(TAG, "createUserWithEmail:failure", e)
            }
        }
    }

    suspend fun signup(username: String, email: String, password: String) {
        try {
            val validationError = signUpValidation(username, email, password)
            if (validationError != null) {
                userSignupCallback?.onError(validationError)
            } else {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val user = authResult.user
                val userID: String = user!!.uid

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()

                user.updateProfile(profileUpdates).await()

                val newUser = User(userID, username, email, "default")

                databaseRef = Firebase.database.getReference("Users").child(userID)
                databaseRef.setValue(newUser).await()

                withContext(Dispatchers.Main) {
                    userSignupCallback?.onSuccess()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                userSignupCallback?.onError("Something went wrong. Please try again later.")
                Log.w(TAG, "createUserWithEmail:failure", e)
            }
        }
    }


    private fun passwordHasDigit(password: String): Boolean {
        for (i in password) {
            if (Character.isDigit(i)) {
                return true
            }
        }
        return false
    }

    private fun passwordHasUppercase(password: String): Boolean {
        for (i in password) {
            if (Character.isUpperCase(i)) {
                return true
            }
        }
        return false
    }

    @Throws(InvalidRegistrationException::class)
    private fun signUpValidation(name: String?, email: String?, password: String): String? {
        if (TextUtils.isEmpty(name)) {
            return "Please enter your username"
        }
        if (TextUtils.isEmpty(email)) {
            return "Please enter your email"
        }
        if (TextUtils.isEmpty(password)) {
            return "Please enter a password"
        }
        if (password.length < 8) {
            return "Password should be at least 8 characters long"
        }
        if (!passwordHasUppercase(password)) {
            return "Password must contain at least one uppercase letter"
        }
        if (!passwordHasDigit(password)) {
            return "Password must contain at least one number"
        }
        return null // No validation errors
    }

    interface UserSignupCallback {
        fun onSuccess()
        fun onError(errorMessage: String)
    }

    interface UserLoginCallback {
        fun onSuccess()
        fun onError(errorMessage: String)
    }

    class InvalidRegistrationException(message: String?) : Exception(message)


    suspend fun updateUserSubscription(selectedSubscription: Subscription) : String {

        var mySubscription = ""

        if (currentUser != null) {
            val userID = currentUser.uid
            val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userID)

            try {
                val dataSnapshot = databaseRef.get().await()

                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.subscription = selectedSubscription.sub_title
                    databaseRef.setValue(user).await()
                    mySubscription = selectedSubscription.sub_title
                    Log.d(ContentValues.TAG, "Subscription change: success")
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "Error updating subscription: $e")
            }
        }
        return mySubscription
    }

    fun logoutUser(){
        if (currentUser != null){
            auth.signOut()
        }
    }
}