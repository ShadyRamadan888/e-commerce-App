package com.example.e_commerceapp.utils

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.data.local.carts_room.CartsDatabase
import com.example.data.local.fav_room.FavoriteDatabase
import com.example.e_commerceapp.ui.adapters.CartsAdapter
import com.example.e_commerceapp.ui.adapters.FavoritesAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SwipeCartToDeleteCallback(private val adapter: CartsAdapter) : ItemTouchHelper.Callback() {

    private val TAG = "SwipeCartToDeleteCallba"
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.position
        try {
            deleteFavorite(adapter.cartsList[position].productId,viewHolder.itemView.context)
            adapter.notifyDataSetChanged()
            adapter.notifyItemRemoved(position)
            adapter.deleteItem(position)

        }catch (e:Exception){
            Log.d(TAG, "SHR: ${e.message}")
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteFavorite(id: Int, context: Context) {
        val cartsDatabase = CartsDatabase.getInstance(context)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                cartsDatabase.cartDao().deleteCart(id)
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }
}