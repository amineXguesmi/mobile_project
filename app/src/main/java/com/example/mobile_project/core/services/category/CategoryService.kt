package com.example.mobile_project.core.services.category

import com.example.mobile_project.core.models.Category

import com.example.mobile_project.core.services.RetrofitHelper
import retrofit2.Call

class CategoryService: CategoryAPI {
    override fun getCategories(): Call<List<Category>> {
        return RetrofitHelper.categoryRetrofitService.getCategories()
    }


}