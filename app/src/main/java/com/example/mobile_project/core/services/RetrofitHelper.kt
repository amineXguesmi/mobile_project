package com.example.mobile_project.core.services

import com.example.mobile_project.core.services.user.UserAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private  val baseUrl ="https://ecommerce-app-mobile-project-75bc9c589beb.herokuapp.com/"
    /**
     * The Retrofit object with Gson converter.
     */
    private val retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    /**
     * A public Api object that exposes the lazy-initialized Retrofit service
     */
    val userRetrofitService : UserAPI by lazy { retrofit.create(UserAPI::class.java)
    }
}