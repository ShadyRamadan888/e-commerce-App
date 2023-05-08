package com.example.e_commerceapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.data.local.fav_room.FavoriteDatabase
import com.example.e_commerceapp.ui.adapters.FavoritesAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SwipeFavToDeleteCallback(private val adapter: FavoritesAdapter) : ItemTouchHelper.Callback() {

    private val TAG = "SwipeToDeleteCallback"

    // Define the direction of the swipe gesture
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
    // Implement the swipe-to-delete functionality
    @SuppressLint("NotifyDataSetChanged")
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        try {
            deleteFavorite(adapter.favList[position].productId, adapter.context)
            adapter.notifyDataSetChanged()
            adapter.notifyItemRemoved(position)
            adapter.deleteItem(position)

        }catch (e:Exception){
            Log.d(TAG, "SHR: ${e.message}")
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteFavorite(id: Int, context: Context) {
        val favoriteDatabase = FavoriteDatabase.getInstance(context)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                favoriteDatabase.favDao().deleteFavorite(id)
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }
}
