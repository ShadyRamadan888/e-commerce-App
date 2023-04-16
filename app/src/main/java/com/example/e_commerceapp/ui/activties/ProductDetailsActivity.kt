package com.example.e_commerceapp.ui.activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private val TAG = "ProductDetailsActivity"
    private lateinit var product_image: ImageView
    private var productId: Int? = null

    lateinit var myViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        assignVariables()
        myViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        productId = intent.getIntExtra("productId", 0)
        //Toast.makeText(this,productId.toString(),Toast.LENGTH_LONG).show()


        getProductById(productId!!)

    }


    private fun getProductById(id: Int) {

        myViewModel.getProduct(id)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                //delay(2000)
                myViewModel.productStateFlow.collect { data ->
                    withContext(Dispatchers.Main) {
                        try {
                            Glide.with(baseContext).load(data!!.data.image).into(product_image)
                        } catch (e: Exception) {
                            Log.d(TAG, "SHR: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }

    private fun assignVariables() {
        product_image = findViewById(R.id.productImage)
    }
}