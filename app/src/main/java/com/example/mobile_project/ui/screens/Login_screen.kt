package com.example.mobile_project.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.core.viewmodels.UserVmFactory
import com.example.mobile_project.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {
    private var _binding: ActivityLoginScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var emailTextEdit: EditText
    private lateinit var passwordTextEdit: EditText
    private lateinit var loginButton:Button
    private lateinit var logoImg:ImageView
    private lateinit var signupButton:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailTextEdit = binding.email
        passwordTextEdit = binding.password
        loginButton = binding.login
        logoImg = binding.logo
        signupButton = binding.signup
        logoImg.setImageResource(com.example.mobile_project.R.drawable.logo)
        loginButton.setOnClickListener {
            onLoginClick()
        }
        viewModel.loginResult.observe(this) { registrationSuccessful ->
            if (registrationSuccessful) {
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            }
        }
        viewModel.loginErrorLiveData.observe(this) { errorMessage ->
            if (errorMessage != null) {
                binding.error.text = errorMessage
            }
        }
        signupButton.setOnClickListener {
            onSignupClick()
        }
    }

    fun onSignupClick() {
        val intent = Intent(this, SignUpScreen::class.java)
        startActivity(intent)
    }
    private fun onLoginClick() {
        val email = emailTextEdit.text.toString()
        val password = passwordTextEdit.text.toString()
        viewModel.loginUser(this,email, password)
    }
    private val viewModel : UserVm by viewModels(factoryProducer = ::UserVmFactory)
}