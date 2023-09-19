package com.movieapp.filmtogo.ui.fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.databinding.FragmentUserSetupSubscriptionBinding
import com.movieapp.filmtogo.modelRemote.Subscription
import com.movieapp.filmtogo.ui.adapters.SubscriptionAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserSetupSubscriptionFragment : Fragment() {

    private lateinit var _binding : FragmentUserSetupSubscriptionBinding
    private val binding get() = _binding
    private var selectedSubscription: Subscription? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUserSetupSubscriptionBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val provideUser = ProvideUser()

        val recyclerView = binding.chooseSubscription
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SubscriptionAdapter(getSubscriptionList()) { clickedSubscription ->
            selectedSubscription = clickedSubscription
        }

        recyclerView.adapter = adapter

        binding.saveBtn.setOnClickListener {
            if (selectedSubscription != null) {
                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        provideUser.updateUserSubscription(selectedSubscription!!)
                        withContext(Dispatchers.Main) {
                            it.findNavController().navigate(R.id.action_userSetupSubscriptionFragment_to_userSetupBillingFragment)
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireView().context,"Something went wrong, check your connection",Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please select a subscription.", Toast.LENGTH_SHORT).show()
            }
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

