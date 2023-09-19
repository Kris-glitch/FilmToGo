package com.movieapp.filmtogo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), ProvideUser.UserLoginCallback {

    private val provideUser = ProvideUser(null, this)
    private lateinit var binding : ActivityLoginBinding


    public override fun onStart() {
        super.onStart()
        val currentUser = provideUser.currentUser
        if (currentUser != null) {
            goToHomepage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.apply {

            newUserBtn.setOnClickListener {
                goToSignup()
            }

            loginBtn.setOnClickListener {
                val email = emailLogin.text.toString().trim()
                val password = passwordLogin.text.toString().trim()

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please provide username and password",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        provideUser.login(email, password)
                    }
                }
            }
        }

    }

    override fun onSuccess() {
        CoroutineScope(Dispatchers.Main).launch {
            goToHomepage()
        }
    }

    override fun onError(errorMessage: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(this@LoginActivity, "Wrong email or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHomepage() {
        val intent : Intent = Intent(this, MainActivity::class.java)
        intent.putExtra("isLoggedIn", true)
        startActivity(intent)
        finish()
    }

    private fun goToSignup() {
        val intent : Intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }
}