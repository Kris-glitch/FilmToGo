package com.movieapp.filmtogo.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.movieapp.filmtogo.Model.User
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.FragmentUserSetupFirstPageBinding

class UserSetupFirstPageFragment : Fragment(R.layout.fragment_user_setup_first_page) {

    private var _binding : FragmentUserSetupFirstPageBinding ?= null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserSetupFirstPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userID = currentUser.uid
            val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userID)

            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(User::class.java)
                        val username = user?.username ?: "User"

                        binding.helloUser.text = "Hello, $username"
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                   Toast.makeText(view.context, "Something went wrong, check your connection", Toast.LENGTH_LONG).show()
                }
            })
        }

        val animate_btn : Animation = AnimationUtils.loadAnimation(view.context, R.anim.animate_btn)
        val animate_txt : Animation = AnimationUtils.loadAnimation(view.context, R.anim.animate_txt)
        val animate_img : Animation = AnimationUtils.loadAnimation(view.context, R.anim.animate_img)


        binding.helloUser.setAnimation(animate_txt)
        binding.profileSetupTxt.setAnimation(animate_txt)
        binding.continueBtn.setAnimation(animate_btn)
        binding.splashImage.setAnimation(animate_img)

    }

}