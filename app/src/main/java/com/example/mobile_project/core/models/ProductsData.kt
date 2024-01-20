package com.example.mobile_project.core.models


import com.google.gson.annotations.SerializedName

data class ProductsData(
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("total")
    val total: Int
)