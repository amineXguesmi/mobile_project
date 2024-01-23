package com.example.mobile_project.core.services.category

import com.example.mobile_project.core.models.Categories
import com.example.mobile_project.core.models.User
import com.example.mobile_project.core.models.UserRegister
import com.example.mobile_project.core.services.RetrofitHelper
import retrofit2.Call

class CategoryService: CategoryAPI {
    override fun getProducts(): Call<Categories> {
        return RetrofitHelper.categoryRetrofitService.getProducts()
    }

}