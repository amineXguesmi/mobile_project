package com.example.mobile_project.core.models


import com.google.gson.annotations.SerializedName

data class UserX(
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("role")
    val role: String
)