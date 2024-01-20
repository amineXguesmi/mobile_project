package com.example.mobile_project.ui.fragments.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.core.viewmodels.UserVmFactory
import com.example.mobile_project.databinding.FragmentEmailBinding
import com.example.mobile_project.ui.screens.LoginScreen
import com.example.mobile_project.ui.screens.SignUpScreen

class EmailFragment : Fragment() {
    private var _binding: FragmentEmailBinding? = null
    private val binding get() = _binding!!
    private lateinit var nextButton:Button
    private lateinit var backLinearLayout: LinearLayout
    private lateinit var email:EditText
    private lateinit var emailAgain:EditText
    private lateinit var error:TextView
    private val emilRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)

        nextButton = binding.next
        email = binding.email
        emailAgain = binding.reenterEmail
        error = binding.error
        backLinearLayout = binding.back
        backLinearLayout.setOnClickListener {
            val intent = Intent(activity, LoginScreen::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.signupErrorLiveData.observe(this) { errorMessage ->
            if (errorMessage != null) {
                error.text = errorMessage
            }
        }
        nextButton.setOnClickListener {
            validateAndNavigateToNextFragment()
        }
    }
    private fun validateAndNavigateToNextFragment() {
        val emailValue = email.text.toString().trim()
        val emailAgainValue = emailAgain.text.toString().trim()

        if (!validateEmail(emailValue)) {
            showError("Invalid email format")
            return
        }
        if (emailValue != emailAgainValue) {
            showError("Emails do not match")
            return
        }
        viewModel.setEmail(emailValue)
        Log.d("EmailFragment", "Email: ${viewModel.userPassword}")
        view?.findNavController()?.navigate(R.id.action_emailFragment_to_passwordFragment)
    }

    private fun validateEmail(email: String): Boolean {
        return emilRegex.matches(email)
    }

    private fun showError(errorMessage: String) {
        error.text = errorMessage
        error.visibility = View.VISIBLE
    }
    private val viewModel : UserVm by viewModels() {
        UserVmFactory()
    }
}