package com.example.e_commerceapp.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Category
import com.example.e_commerceapp.R

class CategoriesAdapter(var categoryList: List<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var pos: Int = 0

    companion object{
        var categoryId:Int = 0
    }

    class ViewHolder(view: View, var catModel: Category? = null) : RecyclerView.ViewHolder(view) {

        init {

        }

        var categoryName: TextView = view.findViewById(R.id.categoryButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.categoryName.text = categoryList[position].name
        holder.categoryName.setOnClickListener {
            pos = position
            categoryId = categoryList[position].id
            notifyDataSetChanged()
        }
        if (pos == position) {
            holder.categoryName.setTextColor(Color.parseColor("#FFFFFF"))
            holder.categoryName.setBackgroundResource(R.drawable.category_click_background)

        } else {
            holder.categoryName.setTextColor(Color.parseColor("#4A9EC5"))
            holder.categoryName.setBackgroundResource(R.drawable.category_background)
        }


//        when (clicked) {
//            false -> {
//                holder.categoryName.setTextColor(Color.parseColor("#4A9EC5"))
//                holder.categoryName.setBackgroundResource(R.drawable.category_background)
//                clicked = true
//            }
//            true -> {
//                holder.categoryName.setTextColor(Color.parseColor("#FFFFFF"))
//                holder.categoryName.setBackgroundResource(R.drawable.category_click_background)
//                clicked = false
//            }
//            else -> {}
//        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}