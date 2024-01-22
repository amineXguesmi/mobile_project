package com.example.mobile_project.core.models

import com.google.gson.annotations.SerializedName

data class ProductsCart(
    @SerializedName("products")
    val products: Product,
    @SerializedName("quantity")
    var quantity: Int
)