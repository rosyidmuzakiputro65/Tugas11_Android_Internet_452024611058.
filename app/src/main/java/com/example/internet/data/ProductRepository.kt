package com.example.internet.data

import com.example.internet.ProductResponse
import com.example.internet.ProductApiService

interface ProductRepository {
    suspend fun getProducts(): List<ProductResponse>
}

class NetworkProductRepository(
    private val productApiService: ProductApiService
) : ProductRepository {
    override suspend fun getProducts(): List<ProductResponse> {
        return productApiService.getProducts()
    }
}
