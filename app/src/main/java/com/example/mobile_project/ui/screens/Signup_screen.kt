package com.example.mobile_project.ui.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import com.example.mobile_project.R
import com.example.mobile_project.databinding.FragmentBasicInformationBinding
import com.example.mobile_project.databinding.FragmentEmailBinding


class SignUpScreen : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    }
    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(navController, null)
    }

}