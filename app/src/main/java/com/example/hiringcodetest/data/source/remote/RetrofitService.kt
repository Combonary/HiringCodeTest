package com.example.hiringcodetest.data.source.remote

import com.example.hiringcodetest.domain.model.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitService {

    @Headers("Accept: application/json")
    @GET("/hiring.json")
    suspend fun getItems(): Response<List<Item>>
}