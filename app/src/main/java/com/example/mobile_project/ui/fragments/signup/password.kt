package com.example.mobile_project.ui.fragments.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.core.viewmodels.UserVmFactory
import com.example.mobile_project.databinding.FragmentEmailBinding
import com.example.mobile_project.databinding.FragmentPasswordBinding
import com.example.mobile_project.ui.screens.SignUpScreen


class PasswordFragment : Fragment() {
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var nextButton: Button
    private lateinit var backLinearLayout: LinearLayout
    private val passwordRegex = Regex("^.{6,}\$")
    private lateinit var password: EditText
    private lateinit var passwordAgain: EditText
    private lateinit var error: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        password = binding.password
        passwordAgain = binding.reenterPassword
        nextButton = binding.next
        error = binding.error
        backLinearLayout = binding.back
        backLinearLayout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_passwordFragment_to_emailFragment)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton.setOnClickListener {
            validateAndNavigateToNextFragment()
        }
    }
    private fun validateAndNavigateToNextFragment() {
        val passwordValue = password.text.toString().trim()
        val passwordAgainValue = passwordAgain.text.toString().trim()

        if (!validatePassword(passwordValue)) {
            showError("Invalid email format")
            return
        }
        if (passwordValue != passwordAgainValue) {
            showError("Emails do not match")
            return
        }
        viewModel.setPassword(passwordValue)

        view?.findNavController()?.navigate(R.id.action_passwordFragment_to_basicInformationFragment)
    }
    private fun validatePassword(email: String): Boolean {
        return passwordRegex.matches(email)
    }

    private fun showError(errorMessage: String) {
        error.text = errorMessage
        error.visibility = View.VISIBLE
    }
    private val viewModel : UserVm by viewModels() {
        UserVmFactory()
    }
}