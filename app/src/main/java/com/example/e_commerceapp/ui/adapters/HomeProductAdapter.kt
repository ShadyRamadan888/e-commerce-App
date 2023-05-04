package com.example.e_commerceapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.local.fav_room.FavoriteDatabase
import com.example.domain.model.FavoritesEntity
import com.example.domain.model.Product
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ProductItemBinding
import com.example.e_commerceapp.ui.activties.ProductDetailsActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HomeProductAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {

//    private val _myData = MutableStateFlow(listOf<Product>())
//    private val myData: StateFlow<List<Product>> = _myData

    private var homeList: List<Product> = listOf()
    private val TAG = "DummyProductAdapter"
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DataBindingUtil.inflate<ProductItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.product_item,
            parent,
            false
        )
        context = parent.context

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = homeList[position]
        holder.bindingSec!!.product = data
        holder.bindingSec!!.executePendingBindings()

        //Navigate to product details(When the user click on a product)
        holder.itemLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("productId", data.id)
            holder.itemView.context.startActivity(intent)
        }

        val favoritesEntity = FavoritesEntity(
            productId = data.id,
            price = data.price,
            image = data.image,
            name = data.name,
            in_favorites = true
        )


        var clicked = 1
        holder.productFavourite.setOnClickListener {

            when (clicked) {
                0 -> {
                    deleteFavorite(data.id)
                    holder.productFavourite.setImageResource(R.drawable.fav_not_selected)
                    clicked = 1
                }
                1 -> {
                    addFavorite(favoritesEntity)
                    holder.productFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    clicked = 0
                }
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            val favoriteDatabase = FavoriteDatabase.getInstance(holder.itemView.context)
            val isFav: Boolean = favoriteDatabase.favDao().isFav(data.id)
            withContext(Dispatchers.Main) {
                if (isFav) {
                    holder.productFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    holder.productFavourite.setImageResource(R.drawable.fav_not_selected)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    fun updateData(newList: List<Product>) {
        homeList = newList
        notifyDataSetChanged()
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun addFavorite(favoritesEntity: FavoritesEntity) {
        val favoriteDatabase = FavoriteDatabase.getInstance(context)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                favoriteDatabase.favDao().insertFavorite(favoritesEntity)
                notifyDataSetChanged()
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }

    private fun deleteFavorite(id: Int) {
        val favoriteDatabase = FavoriteDatabase.getInstance(context)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                favoriteDatabase.favDao().deleteFavorite(id)
                notifyDataSetChanged()
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }

    class ViewHolder(
        private val binding: ProductItemBinding,
        var bindingSec: ProductItemBinding? = null
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            this.bindingSec = binding
        }

        val productFavourite: ImageView = binding.imgFavourite


        //layout
        val itemLayout: LinearLayout = binding.itemLayout
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null && imageUrl.isNotEmpty()) {
                Picasso.get().load(imageUrl).into(view);
            }
        }
    }
}