package com.movieapp.filmtogo.data

import android.content.ContentValues
import android.util.Log
import com.google.android.play.core.integrity.e
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.movieapp.filmtogo.modelRemote.Genre
import kotlinx.coroutines.tasks.await

class ProvideStorage{

    private val provideUser = ProvideUser()
    private val currentUser = provideUser.currentUser
    private val billingDatabaseRef = Firebase.database.getReference("Billing")
    private val recommendationsDatabase = Firebase.database.getReference("Recommendations")


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