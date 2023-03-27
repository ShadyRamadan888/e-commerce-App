package com.example.e_commerceapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Banner
import com.example.e_commerceapp.R
import com.smarteist.autoimageslider.SliderViewAdapter

class BannerAdapter(var bannerList:List<Banner>,var context: Context): SliderViewAdapter<BannerAdapter.SliderViewHolder>() {

    open class SliderViewHolder(view:View): ViewHolder(view) {

        var bannerImage:ImageView = view.findViewById(R.id.imageItem)

    }

    override fun getCount(): Int {
        return bannerList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.banner_item, parent, false)

        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        Glide.with(context).load(bannerList[position].image).into(viewHolder!!.bannerImage)
    }

}




