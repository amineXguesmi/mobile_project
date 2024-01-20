package com.example.mobile_project.core.models


import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("_id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("__v")
    val v: Int
)