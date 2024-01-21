package com.example.mobile_project.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailTextEdit = binding.email
        passwordTextEdit = binding.password
        loginButton = binding.login
        loginButton.setOnClickListener {
            onLoginClick(it)
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
    }

    fun onSignupClick(view: View) {
        val intent = Intent(this, SignUpScreen::class.java)
        startActivity(intent)
    }
    private fun onLoginClick(view: View) {
        val email = emailTextEdit.text.toString()
        val password = passwordTextEdit.text.toString()
        viewModel.loginUser(this,email, password, "customer")
    }
    private val viewModel : UserVm by viewModels() {
        UserVmFactory()
    }
}