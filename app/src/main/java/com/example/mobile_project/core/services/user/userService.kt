package com.example.mobile_project.core.services.user

import com.example.mobile_project.core.models.User
import com.example.mobile_project.core.models.UserRegister
import com.example.mobile_project.core.services.RetrofitHelper
import retrofit2.Call

class UserService: UserAPI {
    override fun loginUser(requestBody: RegistrationRequestBody): Call<User> {
        return RetrofitHelper.userRetrofitService.loginUser(requestBody)
    }

    override fun registerUser(requestBody: RegistrationRequestBody): Call<UserRegister> {
        return RetrofitHelper.userRetrofitService.registerUser(requestBody)
    }

    override fun getUserById(id: String): Call<User> {
        return RetrofitHelper.userRetrofitService.getUserById(id)
    }

}