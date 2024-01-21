package com.example.mobile_project.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.core.viewmodels.ProductVMFactory
import com.example.mobile_project.databinding.ActivityHomePageBinding
import com.example.mobile_project.ui.fragments.product.ProductList
import com.example.mobile_project.ui.fragments.cart_list.Cart

class HomePage : AppCompatActivity() {
    private var _binding: ActivityHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productViewModel.getProducts();
        navController = Navigation.findNavController(this, R.id.home_nav_host_fragment);
        binding.bottomNavigationView2.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> {
                    navController.navigate(R.id.productList)
                    true
                }
                R.id.Cart -> {
                    navController.navigate(R.id.cart)
                    true
                }
                R.id.Favorite -> {
                    navController.navigate(R.id.favoriteProducts)
                    true
                }
                else -> false
            }
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
    private val productViewModel : ProductVM by viewModels() {
        ProductVMFactory()
    }
}