package com.movieapp.filmtogo.data.remote

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.movieapp.filmtogo.data.ProvideUser
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
                val hashMap : HashMap<String, List<Genre>> = hashMapOf("genre" to selectedGenres)

                val databaseRef = recommendationsDatabase.child(userID)

                databaseRef.setValue(hashMap).await()

                Log.d(ContentValues.TAG, "saveDataForRecommendation:success")
            }
        } catch (e: Exception) {
                Log.w(ContentValues.TAG, "saveDataForRecommendation:failure", e)
        }
    }

}