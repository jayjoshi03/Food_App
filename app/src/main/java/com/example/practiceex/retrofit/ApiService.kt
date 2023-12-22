package com.example.practiceex.retrofit

import com.example.practiceex.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/employees")
    fun getUserList() : Call<Response>
}