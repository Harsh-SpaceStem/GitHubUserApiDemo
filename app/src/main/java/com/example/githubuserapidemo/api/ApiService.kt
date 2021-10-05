package com.example.githubuserapidemo.api

import com.example.githubuserapidemo.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsersFromGithubApi(
        @Query("per_page") perPage: Int,
    ): Call<List<Users>>
}