package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.core.viewmodels.ProductVMFactory
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.core.viewmodels.UserVmFactory
import com.example.mobile_project.ui.screens.HomePage
import com.example.mobile_project.ui.screens.LoginScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getUser(this)

        if(viewModel.userIsLogIn){
                productViewModel.getCartProducts(this)

            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }

    }
    private val viewModel : UserVm by viewModels() {
        UserVmFactory()
    }
    private val productViewModel : ProductVM by viewModels() {
        ProductVMFactory()
    }
}