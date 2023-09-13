package com.movieapp.filmtogo.ui.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.databinding.FragmentUserSetupSubscriptionBinding
import com.movieapp.filmtogo.modelRemote.Subscription
import com.movieapp.filmtogo.modelRemote.User
import com.movieapp.filmtogo.ui.adapters.SubscriptionAdapter


class UserSetupSubscriptionFragment : Fragment() {

    private lateinit var _binding : FragmentUserSetupSubscriptionBinding
    private val binding get() = _binding!!
    private var selectedSubscription: Subscription? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserSetupSubscriptionBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.chooseSubscription
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SubscriptionAdapter(getSubscriptionList()) { clickedSubscription ->
            selectedSubscription = clickedSubscription
        }

        recyclerView.adapter = adapter

        binding.saveBtn.setOnClickListener {
            if (selectedSubscription != null) {
                updateUserSubscription(selectedSubscription!!);
                it.findNavController().navigate(R.id.action_userSetupSubscriptionFragment_to_userSetupBillingFragment)
            } else {
                Toast.makeText(requireContext(), "Please select a subscription.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserSubscription(selectedSubscription: Subscription) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userID = currentUser.uid
            val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userID)

            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(User::class.java)
                        user?.subscription = selectedSubscription.sub_title

                        databaseRef.setValue(user).addOnSuccessListener {
                            Log.d(ContentValues.TAG, "Subscription change:success")
                        }.addOnFailureListener {
                            Log.d(ContentValues.TAG, "Subscription change:FAILED")
                            Toast.makeText(view!!.context, "Something went wrong, check your connection", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(view!!.context,"Something went wrong, check your connection",Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun getSubscriptionList(): List<Subscription> {
        return listOf(
            Subscription.SUBSCRIPTION_BASIC,
            Subscription.SUBSCRIPTION_STANDARD,
            Subscription.SUBSCRIPTION_PREMIUM
        )
    }


}

