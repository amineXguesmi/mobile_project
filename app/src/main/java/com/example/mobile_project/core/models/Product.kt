package com.example.mobile_project.core.models


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("brand")
    val brand: Brand,
    @SerializedName("category")
    val category: Category,
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("__v")
    val v: Int
)