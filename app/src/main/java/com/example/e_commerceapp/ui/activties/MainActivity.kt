package com.example.e_commerceapp.ui.activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.data.local.fav_room.FavoriteDatabase
import com.example.domain.model.FavoritesEntity
import com.example.e_commerceapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val favoriteDatabase = FavoriteDatabase.getInstance(this)

        lifecycleScope.launch(Dispatchers.IO) {
//            favoriteDatabase.favDao().insertFavorite(
//                FavoritesEntity(
//                    productId = 2,
//                    price = 2000.0,
//                    image = "image",
//                    name = "product2",
//                    in_favorites = true
//                )
//            )
           // Log.d(TAG, "SHR: ${favoriteDatabase.favDao().getAllFavorites()[0].name}")

        }

        //nav
        navHostFragment =
            supportFragmentManager.findFragmentById(com.example.e_commerceapp.R.id.nav_host_fragment) as NavHostFragment
        bottomNavigationView = findViewById(com.example.e_commerceapp.R.id.bottomNavigationView)
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }
}