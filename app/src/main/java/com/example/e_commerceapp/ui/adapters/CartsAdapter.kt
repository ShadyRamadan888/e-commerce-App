package com.example.e_commerceapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.CartEntity
import com.example.e_commerceapp.R
import com.squareup.picasso.Picasso

class CartsAdapter: RecyclerView.Adapter<CartsAdapter.ViewHolder>() {

     lateinit var cartsList:List<CartEntity>


    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = cartsList[position]
        Picasso.get().load(data.image).into(holder.productImage)
        holder.productName.text = data.name
        holder.productPrice.text = "$"+data.price.toInt().toString()
    }

    override fun getItemCount(): Int {
        return cartsList.size
    }

    fun updateList(newList:List<CartEntity>){
        this.cartsList=newList
    }
    fun deleteItem(position: Int) {
        val mutableList = cartsList.toMutableList()
        mutableList.removeAt(position)
        cartsList = mutableList.toList()
        notifyDataSetChanged()
        notifyItemRemoved(position)
    }
}