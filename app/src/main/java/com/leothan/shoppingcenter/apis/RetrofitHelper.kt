package com.leothan.shoppingcenter.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getAndroid(): RetrofitInterface {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Direcciones().BASE_URL_ANDROID)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
        return retrofitBuilder
    }

    fun getLaravel(): RetrofitInterface {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Direcciones().BASE_URL_LARAVEL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
        return retrofitBuilder
    }
}