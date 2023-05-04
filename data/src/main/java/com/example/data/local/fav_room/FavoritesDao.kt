package com.example.data.local.fav_room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.domain.model.FavoritesEntity

@Dao
interface FavoritesDao {

    @Insert
    fun insertFavorite(favoritesEntity: FavoritesEntity)

    @Query("select * from favorite_table")
    fun getAllFavorites(): List<FavoritesEntity>

    @Query("select in_favorites from favorite_table where productId=:id")
    fun isFav(id:Int):Boolean

    @Query("delete from favorite_table where productId=:id")
    fun deleteFavorite(id:Int)
}