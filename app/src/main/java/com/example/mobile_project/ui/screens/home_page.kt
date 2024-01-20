package com.example.mobile_project.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_project.R
import com.example.mobile_project.ui.fragments.product_list.ProductList

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
    }
}