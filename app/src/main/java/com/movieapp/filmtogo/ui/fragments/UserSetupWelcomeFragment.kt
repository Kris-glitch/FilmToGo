package com.movieapp.filmtogo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.movieapp.filmtogo.modelRemote.User
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.FragmentUserSetupWelcomeBinding

class UserSetupWelcomeFragment : Fragment(R.layout.fragment_user_setup_welcome) {

    private lateinit var _binding : FragmentUserSetupWelcomeBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUserSetupWelcomeBinding.inflate(inflater, container, false)
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
                        val welcomeMessage = getString(R.string.welcome_message, username)
                        binding.helloUser.text = welcomeMessage

                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                   Toast.makeText(view.context, "Something went wrong, check your connection", Toast.LENGTH_LONG).show()
                }
            })
        }

        val animateBtn : Animation = AnimationUtils.loadAnimation(view.context, R.anim.animate_btn)
        val animateTxt : Animation = AnimationUtils.loadAnimation(view.context, R.anim.animate_txt)
        val animateImg : Animation = AnimationUtils.loadAnimation(view.context, R.anim.animate_img)


        binding.helloUser.animation = animateTxt
        binding.profileSetupTxt.animation = animateTxt
        binding.continueBtn.animation = animateBtn
        binding.splashImage.animation = animateImg

        binding.continueBtn.setOnClickListener {
            view.findNavController().navigate(UserSetupWelcomeFragmentDirections
                .actionUserSetupWelcomeFragmentToUserSetupSubscriptionFragment("no"))
        }

    }



}