package com.movieapp.filmtogo.ui.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.databinding.ActivitySignupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity(), ProvideUser.UserSignupCallback {

    private lateinit var binding: ActivitySignupBinding
    private val provideUser = ProvideUser(this, null)

    public override fun onStart() {
        super.onStart()
        val currentUser = provideUser.currentUser
        if (currentUser != null) {
            goToHomepage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.apply {
            signupBtn.setOnClickListener {
                val username: String = usernameSignup.text.toString().trim()
                val email: String = emailSignup.text.toString().trim()
                val password: String = passwordSignup.text.toString().trim()

                CoroutineScope(Dispatchers.IO).launch {
                    provideUser.signup(username, email, password)
                }
            }
        }
    }
    override fun onSuccess() {
        CoroutineScope(Dispatchers.Main).launch {
            goToSetup()
        }
    }

    override fun onError(errorMessage: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(this@SignupActivity, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHomepage() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("isLoggedIn", true)
        startActivity(intent)
        finish()
    }

    private fun goToSetup() {
        Log.d(TAG, "Navigating to SetupActivity")
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("shouldSetupProfile", true)
        startActivity(intent)
        finish()
    }
}