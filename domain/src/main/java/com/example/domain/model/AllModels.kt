package com.example.domain.model

import java.util.*

data class Banner(
    var id: Int,
    var image: String,
    var category: Category,
    var product: Object
)

data class Category(
    var id: Int,
    var name: String,
    var image: String
)

data class Data(
    var banners: List<Banner>,
    var products: List<Product>,
    var data: List<Category>,
    var ad: String
)

data class Product(
    var id: Int,
    var price: Double,
    var old_price: Double,
    var discount: Int,
    var image: String,
    var name: String,
    var description: String,
    var images: List<String>,
    var in_favorites: Boolean,
    var in_cart: Boolean
)

data class ProductRoot(
    var data: Product
)
data class ProductData(
    var data:List<Product>
)
data class CatProduct(
    var data:ProductData
)
data class Root(
    var status: Boolean,
    var message: Object,
    var data: Data
)