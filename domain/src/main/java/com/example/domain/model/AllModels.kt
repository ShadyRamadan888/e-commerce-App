package com.example.domain.model

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
data class AddRemoveFavOrCart(
    var product_id:Int,
)
data class AddRemoveFavRes(
    var id: Int,
    var product: Root
)
data class ProductData(
    var data:List<Product>
)
data class GetAllFav(
    var data: List<FavoriteData>
)
data class FavoriteData(
    val id: Int,
    val product: FavoriteAndCartProductModel
)
data class GetAllFavorites(
    var favoriteData: List<FavoriteData>
)
data class FavoriteProduct(
    val discount: Int,
    val id: Int,
    val image: String,
    val old_price: Double,
    val price: Double
)
data class FavoriteAndCartProductModel(
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val name: String,
    val old_price: Double,
    val price: Double
)
data class CatProduct(
    var data:ProductData
)
data class Root(
    var status: Boolean,
    var message: Object,
    var data: Data
)
data class AddRemoveCartRoot(
    var product_id:Int
)
data class GetCarts(
    var data: List<Product>
)
data class MyResponse <T>(
    val data: T,
    val message: String,
    val status: Boolean
)