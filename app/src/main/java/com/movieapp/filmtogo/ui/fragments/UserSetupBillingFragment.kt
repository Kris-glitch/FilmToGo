package com.movieapp.filmtogo.ui.fragments
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.movieapp.filmtogo.R
import com.movieapp.filmtogo.data.ProvideStorage
import com.movieapp.filmtogo.databinding.FragmentUserSetupBillingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class UserSetupBillingFragment : Fragment() {

    private lateinit var _binding : FragmentUserSetupBillingBinding
    private val binding get() = _binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentUserSetupBillingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val provideStorage = ProvideStorage()

        val args : UserSetupBillingFragmentArgs by navArgs()
        val edit = args.editBilling

        binding.backBtn.visibility = View.GONE
        val saveButton = binding.saveBtn

        if (edit == "edit"){
            saveButton.text = getString(R.string.update)
            binding.backBtn.visibility = View.VISIBLE
        }


        binding.apply {
            backBtn.setOnClickListener {
                Navigation.findNavController(view).navigateUp()
            }
            saveBtn.setOnClickListener {
                val cardNumberValue: String = cardNumber.text.toString()
                val cardHolderValue: String = cardholder.text.toString().trim()
                val cardDateValue: String = cardDate.text.toString().trim()
                val cardCVVValue: String = cardCVV.text.toString()

                try {
                    cardValidation(cardNumberValue, cardHolderValue, cardDateValue, cardCVVValue)

                    try {
                        lifecycleScope.launch(Dispatchers.IO) {
                            provideStorage.saveCardInformation(
                                cardNumberValue,
                                cardHolderValue,
                                cardDateValue,
                                cardCVVValue
                            )

                            if (edit == "edit"){
                                withContext(Dispatchers.Main) {
                                    Navigation.findNavController(view).navigateUp()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    it.findNavController()
                                        .navigate(R.id.action_userSetupBillingFragment_to_userSetuptPreferencesFragment)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "saveCardInformation:failure", e)
                        Toast.makeText(
                            requireView().context,
                            "Something went wrong. Please try again later.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: InvalidRegistrationException) {
                    Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


    private fun isValidCardNumber(cardNumber: String): Boolean {
        val cleanedCardNumber = cardNumber.replace("\\s".toRegex(), "")
        return cleanedCardNumber.matches(Regex("^\\d{13,19}$")) &&
                isLuhnValid(cleanedCardNumber)
    }

    private fun isLuhnValid(cardNumber: String): Boolean {
        val reversedCardNumber = cardNumber.reversed()
        var sum = 0
        var alternate = false

        for (char in reversedCardNumber) {
            if (char.isDigit()) {
                val digit = char.toString().toInt()
                if (alternate) {
                    val doubledDigit = digit * 2
                    sum += if (doubledDigit > 9) doubledDigit - 9 else doubledDigit
                } else {
                    sum += digit
                }
                alternate = !alternate
            }
        }

        return sum % 10 == 0
    }

    private fun isValidExpirationDate(expirationDate: String): Boolean {

        val currentDate = Calendar.getInstance()
        val currentYear = currentDate.get(Calendar.YEAR) % 100
        val currentMonth = currentDate.get(Calendar.MONTH) + 1

        val parts = expirationDate.split("/")
        if (parts.size == 2) {
            try {
                val month = parts[0].toInt()
                val year = parts[1].toInt()

                if (year >= currentYear && month in 1..12) {
                    if (year == currentYear && month < currentMonth) {
                        return false
                    }
                    return true
                }
            } catch (e: NumberFormatException) {
            }
        }

        return false
    }

    class InvalidRegistrationException(message: String?) : Exception(message)

    @Throws(InvalidRegistrationException::class)
    fun cardValidation(cardNumber : String, cardHolder : String, cardDate : String, cardCVV : String) {
        if (TextUtils.isEmpty(cardNumber)) {
            throw InvalidRegistrationException("Please enter card number")
        }
        if (TextUtils.isEmpty(cardHolder)) {
            throw InvalidRegistrationException("Please enter card holder")
        }
        if (TextUtils.isEmpty(cardDate)) {
            throw InvalidRegistrationException("Please enter card expiration date")
        }
        if (TextUtils.isEmpty(cardCVV)) {
            throw InvalidRegistrationException("Please enter card CVV number")
        }
        if (!isValidCardNumber (cardNumber)) {
            throw InvalidRegistrationException("Invalid card number")
        }
        if (!isValidExpirationDate(cardDate)) {
            throw InvalidRegistrationException("Invalid expiration date")
        }
    }

}