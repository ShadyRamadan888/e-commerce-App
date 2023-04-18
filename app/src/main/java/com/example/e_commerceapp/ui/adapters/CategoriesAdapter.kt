package com.example.e_commerceapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.bumptech.glide.Glide
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.activties.ProductDetailsActivity
import kotlin.math.roundToInt

class CategoriesAdapter(var context: Context,var categoryList: List<Product>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var productImage:ImageView = view.findViewById(R.id.product_image)
        var productPrice:TextView = view.findViewById(R.id.product_price)
        var productName:TextView = view.findViewById(R.id.product_name)
        var materialRipple:MaterialRippleLayout = view.findViewById(R.id.cat_material_ripple)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = categoryList[position]
        Glide.with(context).load(data.image).into(holder.productImage)
        holder.productName.text = data.name
        holder.productPrice.text = "$${data.price.roundToInt()}"
        holder.materialRipple.setOnClickListener {
            val intent = Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("productId",data.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}