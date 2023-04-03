package com.example.e_commerceapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Banner
import com.example.domain.model.Product
import com.example.e_commerceapp.R
import kotlin.math.roundToInt

class HomeProductAdapter(var context: Context) :
    RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {

    private var homeList: List<Product> = listOf()
    private val TAG = "DummyProductAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productName: TextView = view.findViewById(R.id.product_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(homeList[position].image).into(holder.productImage)
        holder.productPrice.text = "$" + homeList[position].price.roundToInt()
        holder.productName.text = homeList[position].name
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    fun updateData(homeList: List<Product>){
        this.homeList = homeList
    }
}