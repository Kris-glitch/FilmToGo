package com.movieapp.filmtogo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private lateinit var _binding : FragmentProfileBinding
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var subscription :MutableLiveData<String> = MutableLiveData("")

        val provideUser = ProvideUser()
        lifecycleScope.launch (Dispatchers.IO) {
            var result = provideUser.getUserSubscription().toString()
            lifecycleScope.launch (Dispatchers.Main){
                subscription.value = result
            }
        }

        binding.apply {
            backBtn.setOnClickListener {
                Navigation.findNavController(view).navigateUp()
            }

            subscription.observe(viewLifecycleOwner){s ->
                if (s == "Premium"){
                    getPremiumCard.visibility = View.GONE
                } else {
                    getPremiumCard.visibility = View.VISIBLE
                    getPremiumCard.setOnClickListener{
                        view.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUserSetupSubscriptionFragment("edit"))
                    }
                }
            }


            subscribeArrowRight.setOnClickListener{
                view.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUserSetupSubscriptionFragment("edit"))
            }


        }
    }


}