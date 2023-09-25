package com.movieapp.filmtogo.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.movieapp.filmtogo.data.ProvideUser
import com.movieapp.filmtogo.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var _binding : FragmentProfileBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val provideUser = ProvideUser()

        binding.apply {

            val user = provideUser.currentUser
            if (user != null) {
                usernameEmail.text = user.email
                usernameProfile.text = user.displayName.toString()
            }

            backBtn.setOnClickListener {
                Navigation.findNavController(view).navigateUp()
            }

            provideUser.subscription.observe(viewLifecycleOwner){s ->
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
            walletArrowRight.setOnClickListener{
                view.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUserSetupBillingFragment("edit"))
            }

        }
    }


}