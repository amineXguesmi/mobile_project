package com.example.mobile_project.core.services

import com.example.mobile_project.core.models.User
import com.example.mobile_project.core.models.UserRegister
import retrofit2.Call

class UserService:UserAPI {
    override fun loginUser(requestBody: RegistrationRequestBody): Call<User> {
        return UserRetrofitHelper.retrofitService.loginUser(requestBody)
    }

    override fun registerUser(requestBody: RegistrationRequestBody): Call<UserRegister> {
        return UserRetrofitHelper.retrofitService.registerUser(requestBody)
    }

}