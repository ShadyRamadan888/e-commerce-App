package com.example.e_commerceapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.domain.model.ProductModel
import com.example.e_commerceapp.R
import java.text.DecimalFormat

class ProductAdapter: BaseAdapter(){

    private lateinit var list: List<ProductModel>

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view:View = LayoutInflater.from(parent?.context).inflate(R.layout.product_item,null)

        val productImage:ImageView = view.findViewById(R.id.product_image)
        val productPrice:TextView = view.findViewById(R.id.product_price)
        val productRate:TextView = view.findViewById(R.id.product_rate)
        val addButton:Button = view.findViewById(R.id.add_btn)

        Glide.with(parent!!.context).load(list[position].images[0]).into(productImage)
        productPrice.text ="$"+ list[position].price
        productRate.text = "" + DecimalFormat("#.#").format(list[position].rating).toDouble()



        //Image clicking for details
        productImage.setOnClickListener{
            Toast.makeText(parent.context,"Image: $position",Toast.LENGTH_LONG).show()
        }

        //Adding button to add product to cart
        addButton.setOnClickListener {
            Toast.makeText(parent.context,"button: $position",Toast.LENGTH_LONG).show()
        }



        return view
    }

    fun submitList(list:List<ProductModel>?){
        this.list = list!!
    }
}