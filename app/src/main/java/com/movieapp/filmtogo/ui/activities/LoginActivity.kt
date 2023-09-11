package com.movieapp.filmtogo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityLoginBinding


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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        binding.apply {

            newUserBtn.setOnClickListener(View.OnClickListener {
                goToSignup()
            })

            loginBtn.setOnClickListener(View.OnClickListener {
                val email = emailLogin.text.toString().trim()
                val password = passwordLogin.text.toString().trim()

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this@LoginActivity, "Please provide username and password", Toast.LENGTH_LONG).show()
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                     OnCompleteListener { task ->
                         if (task.isSuccessful){
                             goToHomepage()
                         } else {
                             Toast.makeText(this@LoginActivity, "Wrong email or password", Toast.LENGTH_SHORT).show()
                         }
                     })
                }
            })
        }

    }

    private fun goToHomepage() {
        var intent : Intent = Intent(this, MainActivity::class.java)
        intent.putExtra("isLoggedIn", true)
        startActivity(intent)
        finish()
    }

    private fun goToSignup() {
        var intent : Intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }
}