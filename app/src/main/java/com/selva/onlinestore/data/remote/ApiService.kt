package com.selva.onlinestore.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("random")
    suspend fun getProducts(): Response<DuckItem>

    companion object {
        const val BASE_URL = "https://random-d.uk/api/v2/"
    }
}