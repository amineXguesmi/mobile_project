package com.example.mobile_project.core.services.user

import com.example.mobile_project.core.models.User
import com.example.mobile_project.core.models.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
    @POST("api/auth/login")
    fun loginUser(@Body requestBody: RegistrationRequestBody): Call<User>

    @POST("api/users")
    fun registerUser(@Body requestBody: RegistrationRequestBody): Call<UserRegister>

    @GET("api/users/{id}")
    fun getUserById(@Path("id") id: String): Call<User>

}

data class RegistrationRequestBody(
    val email: String,
    val password: String,
    val role: String
)