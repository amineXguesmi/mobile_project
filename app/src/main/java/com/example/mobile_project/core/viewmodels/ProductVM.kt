package com.example.mobile_project.core.viewmodels

import ProductService
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.models.ProductsData
import com.example.mobile_project.core.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductVM(private val productService: ProductService = ProductService()) : ViewModel() {
    val products : MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    var error : MutableLiveData<String> = MutableLiveData<String>()
    val product : MutableLiveData<Product> = MutableLiveData<Product>()
    fun getProducts() {

        val call: Call<ProductsData> = productService.getProducts()

        call.enqueue(object : Callback<ProductsData> {
            override fun onResponse(call: Call<ProductsData>, response: Response<ProductsData>) {
                if (response.isSuccessful) {
                    val result: ProductsData? = response.body()
                    if (result != null) {
                        products.postValue(result.products)
                        println(result.toString())
                    }
                } else {
                    println("null response")
                    error.postValue("an error has occured while fetching data")
                }
            }

            override fun onFailure(call: Call<ProductsData>, t: Throwable) {
                error.postValue(t.message)
                println("failure")
                println(t.message.toString())
            }
        })
    }

    fun getProductById(id: String) {

        val call: Call<Product> = productService.getProductById(id)

        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val result: Product? = response.body()
                    if (result != null) {
                        product.postValue(result!!)
                    }
                } else {
                    error.postValue("an error has occured while fetching data")
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                error.postValue(t.message)
            }
        })
    }
}