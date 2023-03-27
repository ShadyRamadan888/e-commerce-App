package com.example.e_commerceapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Category
import com.example.e_commerceapp.R

class CategoriesAdapter(var categoryList: List<Category>):RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var categoryName:TextView = view.findViewById(R.id.categoryButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.categoryName.text = categoryList[position].name
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}