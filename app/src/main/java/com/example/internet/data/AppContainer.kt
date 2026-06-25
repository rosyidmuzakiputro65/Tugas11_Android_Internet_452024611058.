package com.example.internet.data

import com.example.internet.ProductApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val productRepository: ProductRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://fakestoreapi.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        NetworkProductRepository(retrofitService)
    }
}
