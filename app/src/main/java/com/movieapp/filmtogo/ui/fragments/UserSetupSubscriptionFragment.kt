package com.movieapp.filmtogo.ui.fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
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

        val args : UserSetupSubscriptionFragmentArgs by navArgs()
        val edit = args.editSubscription

        binding.backBtn.visibility = View.GONE

        val saveButton = binding.saveBtn


        if (edit == "edit"){
            saveButton.text = getString(R.string.update)
            binding.backBtn.visibility = View.VISIBLE
        }

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }


        binding.saveBtn.setOnClickListener {
            if (selectedSubscription != null) {
                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        provideUser.updateUserSubscription(selectedSubscription!!)

                        if (edit == "edit"){
                            withContext(Dispatchers.Main) {
                                Navigation.findNavController(view).navigateUp()
                            }
                        }
                        else{
                            withContext(Dispatchers.Main) {
                                it.findNavController().navigate(UserSetupSubscriptionFragmentDirections.actionUserSetupSubscriptionFragmentToUserSetupBillingFragment("no"))
                            }
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

