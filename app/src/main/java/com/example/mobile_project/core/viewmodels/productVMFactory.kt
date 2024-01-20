package com.example.mobile_project.core.viewmodels

import ProductService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ProductVMFactory (private val productService: ProductService = ProductService()) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductVM::class.java)) {
            return ProductVM(productService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}