package com.example.e_commerceapp.ui.activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityCategoryProductsBinding
import com.example.e_commerceapp.ui.adapters.CategoriesAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.example.e_commerceapp.utils.FacebookShimmerFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CategoryProducts : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: ActivityCategoryProductsBinding
    private lateinit var facebookShimmerFactory: FacebookShimmerFactory
    private val TAG = "CategoryProducts"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        assignVariables()

        setUpToolBar()

        facebookShimmerFactory.startShimmer()

        val categoryID = intent.getIntExtra("catId", 0)
        val categoryName = intent.getStringExtra("CatName")
        binding.catToolBar.title = categoryName.toString()



        getData(categoryID)
    }


    private fun getData(category_id: Int) {

        viewModel.categoryProducts(category_id)
        lifecycleScope.launch(Dispatchers.IO) {
            delay(2000)
            viewModel.viewStateFlow.collect { data ->
                try {
                    withContext(Dispatchers.Main) {
                        categoriesAdapter =
                            CategoriesAdapter(this@CategoryProducts, data.catProduct!!.data.data)
                        recyclerView.adapter = categoriesAdapter
                        facebookShimmerFactory.stopShimmer()
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "SHR: ${e.message}")
                }
            }
        }
    }

    private fun assignVariables() {
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        recyclerView = binding.categoryProductsRecycler
        facebookShimmerFactory = FacebookShimmerFactory(binding.shimmerFrameLayout)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.catToolBar)
        binding.catToolBar.setBackgroundColor(ContextCompat.getColor(this, R.color.app))
        binding.catToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.catToolBar.setNavigationOnClickListener {
            // Handle click on the icon
            onBackPressed()
        }
    }
}