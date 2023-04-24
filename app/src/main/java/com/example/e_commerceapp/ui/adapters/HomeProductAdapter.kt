package com.example.e_commerceapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.AddRemoveFavOrCart
import com.example.domain.model.Product
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.activties.ProductDetailsActivity
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class HomeProductAdapter(var context: Context, var viewModel: ProductsViewModel) :
    RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {

    private var homeList: List<Product> = listOf()
    private val TAG = "DummyProductAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productName: TextView = view.findViewById(R.id.product_name)
        val productFavourite: ImageView = view.findViewById(R.id.img_favourite)


        //layout
        val item_layout: LinearLayout = view.findViewById(R.id.item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = homeList[position]
        Glide.with(context).load(data.image).into(holder.productImage)
        holder.productPrice.text = "$" + data.price.roundToInt()
        holder.productName.text = data.name
        //Navigate to product details(When the user click on a product)
        holder.item_layout.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("productId", data.id)
            context.startActivity(intent)
        }

        if (data.in_favorites) {
            holder.productFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            holder.productFavourite.setImageResource(R.drawable.fav_not_selected)
        }
        holder.productFavourite.setOnClickListener {
            val addRemoveFavOrCart = AddRemoveFavOrCart(data.id)
            addFavById(addRemoveFavOrCart)
        }
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    fun updateData(homeList: List<Product>) {
        this.homeList = homeList
        notifyDataSetChanged()
    }

    private fun addFavById(addRemoveFavOrCart: AddRemoveFavOrCart) {
        viewModel.addFavoriteById(addRemoveFavOrCart)
        viewModel.viewModelScope.launch {
            try {
                viewModel.addFavStateFlow.collect {
                    withContext(Dispatchers.Main){
                        try {
                            Toast.makeText(context, it!!.product.data.products[0].name, Toast.LENGTH_SHORT).show()
                        }catch (e:Exception){
                            Log.d(TAG, "SHRE1: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "SHRE2: ${e.message}")
            }
        }
    }
}