package com.example.mobile_project.core.models


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user")
    val user: UserX
)