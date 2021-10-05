package com.example.githubuserapidemo

import com.example.githubuserapidemo.api.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://api.github.com/"

    private val okHttpClient = OkHttpClient()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiServices: ApiService = getRetrofit().create(ApiService::class.java)

}