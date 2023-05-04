package com.example.e_commerceapp.ui.activties

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.domain.model.AddRemoveCartRoot
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.adapters.ImgDetailsAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.example.e_commerceapp.utils.FacebookShimmerFactory
import com.example.ui.activties.MainActivity
import com.facebook.shimmer.ShimmerFrameLayout
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
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var imgDetailsAdapter: ImgDetailsAdapter
    private lateinit var imgSliderView: SliderView
    lateinit var facebookShimmerFactory: FacebookShimmerFactory
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var addCartBtn: Button

    lateinit var myViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        assignVariables()
        setUpToolBar()

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
                myViewModel.viewStateFlow.collect { data ->
                    withContext(Dispatchers.Main) {
                        try {
                            imgDetailsAdapter = ImgDetailsAdapter(this@ProductDetailsActivity)
                            imgDetailsAdapter.setList(data.product!!.data.images)
                            imgSliderView.setSliderAdapter(imgDetailsAdapter)
                            productName.text = data.product.data.name
                            productPrice.text = "$${data.product.data.price.roundToInt()}"
                            productDesc.text = data.product.data.description
                            facebookShimmerFactory.stopShimmer()
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
        toolbar = findViewById(R.id.catToolBar)
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout)
        facebookShimmerFactory = FacebookShimmerFactory(shimmerFrameLayout)
        imgSliderView = findViewById(R.id.img_details_slider)
        productName = findViewById(R.id.product_name)
        productPrice = findViewById(R.id.product_price)
        productDesc = findViewById(R.id.product_desc)
        loadMore = findViewById(R.id.load_more_button)
        //Cart
        addCartBtn = findViewById(R.id.addCartBtn)
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

    override fun onResume() {
        super.onResume()
        facebookShimmerFactory.startShimmer()
    }

    private fun setUpToolBar() {
        (this as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.app))
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            // Handle click on the icon
            onBackPressed()
        }
    }

}