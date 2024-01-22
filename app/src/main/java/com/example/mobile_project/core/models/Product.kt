package com.example.mobile_project.core.models


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("brand")
    val brand: BrandX,
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("__v")
    val v: Int
)