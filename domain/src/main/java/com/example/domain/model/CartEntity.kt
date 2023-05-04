package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts_table")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var productId: Int,
    var price: Double,
    var image: String,
    var name: String,
    var in_cart: Boolean
)
