package com.example.e_commerceapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.FavoritesEntity
import com.example.e_commerceapp.R

class FavoritesAdapter(var context: Context) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    lateinit var favList: List<FavoritesEntity>

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = favList[position]
        holder.itemView.tag = position
        holder.productName.text = data.name
        holder.productPrice.text = data.price.toString()
        Glide.with(context).load(data.image).into(holder.productImage)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    fun setList(list: List<FavoritesEntity>) {
        this.favList = list
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val mutableList = favList.toMutableList()
        mutableList.removeAt(position)
        favList = mutableList.toList()
        notifyDataSetChanged()
        notifyItemRemoved(position)
    }
}