package com.example.mobile_project.core.services.category

import com.example.mobile_project.core.models.Categories
import com.example.mobile_project.core.models.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryAPI {
    @GET("api/categories")
    fun getCategories(): Call<List<Category>>

}