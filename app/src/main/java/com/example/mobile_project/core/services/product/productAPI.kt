package com.example.mobile_project.core.services.product

import com.example.mobile_project.core.models.ProductsData
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {
    @GET("api/products")
    fun getProducts(): Call<ProductsData>
}