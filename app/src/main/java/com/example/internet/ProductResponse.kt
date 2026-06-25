package com.example.internet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: Double,
    @Json(name = "description") val description: String,
    @Json(name = "image") val imageUrl: String // Di tutorial tertulis imageUrl, tapi biasanya API pake "image". Saya sesuaikan mappingnya jika perlu.
)
