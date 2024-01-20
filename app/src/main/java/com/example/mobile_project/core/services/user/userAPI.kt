package com.example.mobile_project.core.services.user

import com.example.mobile_project.core.models.User
import com.example.mobile_project.core.models.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST("api/auth/login")
    fun loginUser(@Body requestBody: RegistrationRequestBody): Call<User>

    @POST("api/users")
    fun registerUser(@Body requestBody: RegistrationRequestBody): Call<UserRegister>
}

data class RegistrationRequestBody(
    val email: String,
    val password: String,
    val role: String
)