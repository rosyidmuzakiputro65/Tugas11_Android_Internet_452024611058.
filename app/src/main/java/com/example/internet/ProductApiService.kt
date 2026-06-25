package com.example.internet

import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductResponse>
}
