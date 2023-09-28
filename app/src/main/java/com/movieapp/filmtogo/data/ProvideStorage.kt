package com.movieapp.filmtogo.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.movieapp.filmtogo.modelRemote.Genre
import com.movieapp.filmtogo.modelRemote.Movie
import kotlinx.coroutines.tasks.await

class ProvideStorage{

    private val provideUser = ProvideUser()
    private val currentUser = provideUser.currentUser
    private val billingDatabaseRef = Firebase.database.getReference("Billing")
    private val recommendationsDatabase = Firebase.database.getReference("Recommendations")
    private val favouritesDatabase = Firebase.database.getReference("Favourites")


    suspend fun saveCardInformation(cardNumber : String, cardHolder : String, cardDate : String, cardCVV : String) {
        try {
            if (currentUser != null) {
                val userID = currentUser.uid
                val hashMap: HashMap<String, String> = createBillingHash(userID, cardNumber, cardHolder, cardDate, cardCVV)

                val databaseRef = billingDatabaseRef.child(userID)
                databaseRef.setValue(hashMap).await()
                Log.d(ContentValues.TAG, "saveCardInformation:success")
            }
        } catch (e: Exception) {
                Log.w(ContentValues.TAG, "saveCardInformation:failure", e)
            }
        }


    private fun createBillingHash(userID: String, cardNumber: String, cardHolder: String, cardDate: String, cardCVV: String): HashMap<String, String> {
        return hashMapOf(
            "id" to userID,
            "cardNumber" to cardNumber,
            "cardHolder" to cardHolder,
            "cardDate" to cardDate,
            "cardCVV" to cardCVV
        )
    }

    suspend fun saveFavourite(userID: String, movieID: String, movie:Movie) {
        try {
            val databaseRef = favouritesDatabase.child(userID).child(movieID)

            databaseRef.setValue(movie).await()

            Log.d(ContentValues.TAG, "saveFavourite: success")
        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "saveFavourite: failure", e)
        }
    }
    fun getFavoriteMoviesForUser(userID: String): LiveData<ArrayList<Movie>> {
        val favoriteMoviesLiveData = MutableLiveData<ArrayList<Movie>>()
        val databaseRef = favouritesDatabase.child(userID)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val favoriteMovies = ArrayList<Movie>()

                for (childSnapshot in dataSnapshot.children) {
                    val movie = childSnapshot.getValue(Movie::class.java)
                    if (movie != null) {
                        favoriteMovies.add(movie)
                    }
                }

                favoriteMoviesLiveData.value = favoriteMovies
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        return favoriteMoviesLiveData
    }

    suspend fun deleteFavoriteMovie(userID: String, movieID: String) {
        try {
            val databaseRef = favouritesDatabase.child(userID).child(movieID)

            databaseRef.removeValue().await()

            Log.d(ContentValues.TAG, "deleteFavoriteMovie: success")
        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "deleteFavoriteMovie: failure", e)
        }
    }

    suspend fun saveDataForRecommendation(selectedGenres : List<Genre>) {
        try {
            if (currentUser != null) {
                val userID = currentUser.uid

                val databaseRef = recommendationsDatabase.child(userID)

                databaseRef.setValue(selectedGenres).await()

                Log.d(ContentValues.TAG, "saveDataForRecommendation:success")
            }
        } catch (e: Exception) {
                Log.w(ContentValues.TAG, "saveDataForRecommendation:failure", e)
        }
    }

    suspend fun getPreferredGenres() : List<Genre>?{
        try {
            if (currentUser != null) {
                val userID = currentUser.uid
                val databaseRef = recommendationsDatabase.child(userID)
                val dataSnapshot = databaseRef.get().await()

                return if (dataSnapshot.exists()) {
                    val selectedGenres = dataSnapshot.getValue(object : GenericTypeIndicator<List<Genre>>() {})
                    selectedGenres
                } else {
                    Log.w(ContentValues.TAG, "getPreferredGenres:data doesn't exist")
                    null
                }
            }
        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "getPreferredGenres:failure", e)

        }
        return null

    }




}