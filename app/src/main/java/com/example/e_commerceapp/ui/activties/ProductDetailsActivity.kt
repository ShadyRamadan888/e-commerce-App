package com.example.e_commerceapp.ui.activties

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.*
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.adapters.ImgDetailsAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private val TAG = "ProductDetailsActivity"
    private var productId: Int? = null
    private lateinit var productName: TextView
    private lateinit var productPrice: TextView
    private lateinit var productDesc: TextView
    private lateinit var loadMore: TextView
    private lateinit var imgDetailsAdapter: ImgDetailsAdapter
    private lateinit var imgSliderView: SliderView

    lateinit var myViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        assignVariables()
        myViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productId = intent.getIntExtra("productId", 0)
        loadMoreAndLessFactory()
        getProductById(productId!!)
    }

    @SuppressLint("SetTextI18n")
    private fun getProductById(id: Int) {

        myViewModel.getProduct(id)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                myViewModel.productStateFlow.collect { data ->
                    withContext(Dispatchers.Main) {
                        try {
                            imgDetailsAdapter = ImgDetailsAdapter(this@ProductDetailsActivity)
                            imgDetailsAdapter.setList(data!!.data.images)
                            imgSliderView.setSliderAdapter(imgDetailsAdapter)
                            productName.text = data.data.name
                            productPrice.text = "$${data.data.price.roundToInt()}"
                            productDesc.text = data.data.description
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
        imgSliderView = findViewById(R.id.img_details_slider)
        productName = findViewById(R.id.product_name)
        productPrice = findViewById(R.id.product_price)
        productDesc = findViewById(R.id.product_desc)
        loadMore = findViewById(R.id.load_more_button)
    }

    private fun loadMoreAndLessFactory() {
        var clicked = true
        loadMore.setOnClickListener {
            if (clicked) {
                productDesc.maxLines = Integer.MAX_VALUE
                productDesc.ellipsize = null
                loadMore.text = "Load Less"
                clicked = false
                //loadMore.visibility = View.GONE
            } else {
                productDesc.maxLines = 5
                productDesc.ellipsize = TextUtils.TruncateAt.END
                loadMore.text = "Load More"
                clicked = true
                //loadMore.visibility = View.GONE
            }
        }
    }
}