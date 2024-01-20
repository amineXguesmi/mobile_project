package com.example.mobile_project.core.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRetrofitHelper {

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
    val retrofitService : UserAPI by lazy { retrofit.create(UserAPI::class.java)
    }
}