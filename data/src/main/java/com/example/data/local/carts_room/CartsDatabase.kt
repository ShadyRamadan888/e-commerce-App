package com.example.data.local.carts_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.fav_room.FavoriteDatabase
import com.example.domain.model.CartEntity

@Database(entities = [CartEntity::class], version = 1)
abstract class CartsDatabase :RoomDatabase(){

    abstract fun cartDao(): CartsDao

    companion object {
        private const val DATA_BASE = "carts.db"

        @Volatile
        private var instance: CartsDatabase? = null

        fun getInstance(context: Context): CartsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context):CartsDatabase{
            return Room.databaseBuilder(
                context,
                CartsDatabase::class.java, DATA_BASE
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}