package com.movieapp.filmtogo.ui.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.ActivitySignupBinding
import com.movieapp.filmtogo.modelRemote.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivitySignupBinding
    private lateinit var databaseRef : DatabaseReference

    public override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToHomepage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.apply {

            signupBtn.setOnClickListener(View.OnClickListener {

                val username : String = usernameSignup.text.toString().trim()
                val email : String = emailSignup.text.toString().trim()
                val password : String = passwordSignup.text.toString().trim()

                try {
                    signUpValidation(username, email, password)
                    lifecycleScope.launch(Dispatchers.IO) {
                        createNewUser(username, email, password)
                    }
                } catch (e: InvalidRegistrationException) {
                    Toast.makeText(this@SignupActivity, "" + e.message, Toast.LENGTH_SHORT).show()
                }
            });
        }


    }

    private suspend fun createNewUser(username: String, email: String, password: String) {
        try {
            auth = FirebaseAuth.getInstance()
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            val userID: String = user!!.uid

            val newUser = User(userID, username, email, "default", "")

            databaseRef = Firebase.database.getReference("Users").child(userID)
            databaseRef.setValue(newUser).await()

            Log.d(TAG, "createUserWithEmail:success")

            runOnUiThread {
                goToSetup()
            }
        } catch (e: Exception) {
            runOnUiThread {
                Toast.makeText(
                    baseContext,
                    "Something went wrong. Please try again later.",
                    Toast.LENGTH_LONG
                ).show()
                Log.w(TAG, "createUserWithEmail:failure", e)
            }
        }
    }



    private fun goToHomepage() {
        var intent : Intent = Intent(this, MainActivity::class.java)
        intent.putExtra("isLoggedIn", true)
        startActivity(intent)
        finish()
    }

    private fun goToSetup() {
        Log.d(TAG, "Navigating to SetupActivity")
        var intent : Intent = Intent(this, MainActivity::class.java)
        intent.putExtra("shouldSetupProfile", true)
        startActivity(intent)
        finish()
    }

    class InvalidRegistrationException(message: String?) : Exception(message)

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
    fun signUpValidation(name: String?, email: String?, password: String) {
        if (TextUtils.isEmpty(name)) {
            throw InvalidRegistrationException("Please enter your username")
        }
        if (TextUtils.isEmpty(email)) {
            throw InvalidRegistrationException("Please enter your email")
        }
        if (TextUtils.isEmpty(password)) {
            throw InvalidRegistrationException("Please enter a password")
        }
        if (password.length < 8) {
            throw InvalidRegistrationException("Password should be at least 8 characters long")
        }
        if (!passwordHasUppercase(password)) {
            throw InvalidRegistrationException("Password must contain at least one uppercase letter")
        }
        if (!passwordHasDigit(password)) {
            throw InvalidRegistrationException("Password must contain at least one number")
        }
    }
}