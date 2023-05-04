package com.example.data.local.carts_room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.domain.model.CartEntity

@Dao
interface CartsDao {
    @Insert
    fun addToCart(cartEntity: CartEntity)

    @Query("select * from carts_table")
    fun getAllCarts():List<CartEntity>

    @Query("delete from carts_table where productId=:id")
    fun deleteCart(id:Int)

    @Query("select in_cart from carts_table where productId=:id")
    fun isInCart(id:Int):Boolean
}