package com.example.mobile_project.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.core.viewmodels.ProductVMFactory
import com.example.mobile_project.databinding.ActivityHomePageBinding
import com.example.mobile_project.databinding.ActivityLoginScreenBinding
import com.example.mobile_project.databinding.FragmentEmailBinding
import com.example.mobile_project.ui.fragments.product.ProductList

class HomePage : AppCompatActivity() {
    private var _binding: ActivityHomePageBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ProductList())
        productViewModel.getProducts();
        binding.bottomNavigationView2.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> {
                    replaceFragment(ProductList())
                    true
                }
                R.id.Cart -> {
                    replaceFragment(ProductList())
                    true
                }
                R.id.Favorite -> {
                    replaceFragment(ProductList())
                    true
                }
                else -> false
            }
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    private val productViewModel : ProductVM by viewModels() {
        ProductVMFactory()
    }
}