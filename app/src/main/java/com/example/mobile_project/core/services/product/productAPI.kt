package com.example.mobile_project.core.services.product

import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.models.ProductByCategory
import com.example.mobile_project.core.models.ProductsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {
    @GET("api/products")
    fun getProducts(): Call<ProductsData>

    @GET("api/products/{id}")
    fun getProductById(@Path("id") id: String): Call<Product>

    @GET("/api/products/category/{id}")
    fun getProductByCategory(@Path("id") id: String): Call<ProductByCategory>
}