package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var productId: Int,
    var price: Double,
    var image: String,
    var name: String,
    var in_favorites: Boolean
)
