package com.example.internet

sealed class ListItem {
    data class Header(val title: String) : ListItem()
    data class NumberItem(val number: Int) : ListItem()
    data class ColorItem(val hex: String, val name: String) : ListItem()
    data class ProductItem(val product: ProductResponse) : ListItem()
}
