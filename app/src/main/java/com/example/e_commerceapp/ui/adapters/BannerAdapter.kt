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
import com.facebook.shimmer.ShimmerFrameLayout
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import javax.inject.Inject

class BannerAdapter @Inject constructor() : SliderViewAdapter<BannerAdapter.SliderViewHolder>() {

    private lateinit var bannerList: List<Banner>

    open class SliderViewHolder(view: View) : ViewHolder(view) {

        var bannerImage: ImageView = view.findViewById(R.id.imageItem)

    }

    override fun getCount(): Int {
        return bannerList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val view =
            LayoutInflater.from(parent!!.context).inflate(R.layout.banner_item, parent, false)

        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        Picasso.get().load(bannerList[position].image).into(viewHolder!!.bannerImage)
    }

    fun updateData(bannerList: List<Banner>) {
        this.bannerList = bannerList
    }

}




