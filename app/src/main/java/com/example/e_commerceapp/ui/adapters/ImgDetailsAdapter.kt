package com.example.e_commerceapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.smarteist.autoimageslider.SliderViewAdapter

class ImgDetailsAdapter(var context: Context) :
    SliderViewAdapter<ImgDetailsAdapter.SliderViewHolder>() {

    private lateinit var imageList: List<String>

    class SliderViewHolder(view: View) : ViewHolder(view) {
        var detailsImage: ImageView = view.findViewById(R.id.imageItem)
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val view =
            LayoutInflater.from(parent!!.context).inflate(R.layout.img_details_item, parent, false)

        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        Glide.with(context).load(imageList[position]).into(viewHolder!!.detailsImage)
    }

    fun setList(list: List<String>) {
        this.imageList = list
    }
}