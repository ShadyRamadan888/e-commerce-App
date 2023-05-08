package com.example.e_commerceapp.ui.activties

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.data.local.carts_room.CartsDatabase
import com.example.domain.model.CartEntity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityCategoryProductsBinding.inflate
import com.example.e_commerceapp.databinding.ActivityProductDetailsBinding
import com.example.e_commerceapp.ui.adapters.ImgDetailsAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.example.e_commerceapp.utils.FacebookShimmerFactory
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private val TAG = "ProductDetailsActivity"
    private var productId: Int? = null
    private lateinit var cartEntity: CartEntity
    private lateinit var imgDetailsAdapter: ImgDetailsAdapter
    private lateinit var imgSliderView: SliderView
    private lateinit var facebookShimmerFactory: FacebookShimmerFactory
    private lateinit var binding: ActivityProductDetailsBinding

    lateinit var myViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        assignVariables()
        setUpToolBar()

        myViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productId = intent.getIntExtra("productId", 0)
        loadMoreAndLessFactory()
        getProductById(productId!!)


        binding.addCartBtn.setOnClickListener {
            addCart(cartEntity)
        }


    }

    private fun addCart(cartEntity: CartEntity) {
        val cartsDatabase = CartsDatabase.getInstance(this)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                cartsDatabase.cartDao().addToCart(cartEntity)
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
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
                            binding.productName.text = data.product.data.name
                            binding.productPrice.text = "$${data.product.data.price.roundToInt()}"
                            binding.productDesc.text = data.product.data.description
                            cartEntity = CartEntity(
                                data.product.data.id,
                                data.product.data.price,
                                data.product.data.image,
                                data.product.data.name,
                                in_cart = true
                            )
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
        facebookShimmerFactory = FacebookShimmerFactory(binding.shimmerFrameLayout)
        imgSliderView = findViewById(R.id.img_details_slider)
    }

    private fun loadMoreAndLessFactory() {
        var clicked = true
        binding.loadMoreButton.setOnClickListener {
            if (clicked) {
                binding.productDesc.maxLines = Integer.MAX_VALUE
                binding.productDesc.ellipsize = null
                binding.loadMoreButton.text = "Load Less"
                clicked = false
                //loadMore.visibility = View.GONE
            } else {
                binding.productDesc.maxLines = 5
                binding.productDesc.ellipsize = TextUtils.TruncateAt.END
                binding.loadMoreButton.text = "Load More"
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
        (this as AppCompatActivity).setSupportActionBar(binding.catToolBar)
        binding.catToolBar.setBackgroundColor(ContextCompat.getColor(this, R.color.app))
        binding.catToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.catToolBar.setNavigationOnClickListener {
            // Handle click on the icon
            onBackPressed()
        }
    }

}