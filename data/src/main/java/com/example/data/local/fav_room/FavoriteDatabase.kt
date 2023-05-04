package com.example.data.local.fav_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.model.FavoritesEntity
import javax.inject.Inject

@Database(entities = [FavoritesEntity::class], version = 5, exportSchema = false)
abstract class FavoriteDatabase() : RoomDatabase() {

    abstract fun favDao(): FavoritesDao

    companion object {
        private const val DATABASE_NAME = "favorite.db"

        @Volatile
        private var instance: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): FavoriteDatabase {
            return Room.databaseBuilder(
                context,
                FavoriteDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}