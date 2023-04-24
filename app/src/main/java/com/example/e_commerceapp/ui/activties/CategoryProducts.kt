package com.example.e_commerceapp.ui.activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        facebookShimmerFactory.startShimmer()

        val categoryID = intent.getIntExtra("catId", 0)
        val categoryName = intent.getStringExtra("CatName")
        binding.textToolbar.text = categoryName.toString()

        //getData(categoryID)
    }


//    private fun getData(category_id: Int) {
//
//        viewModel.categoryProducts(category_id)
//        lifecycleScope.launch(Dispatchers.IO) {
//            delay(2000)
//            viewModel.catProductStateFlow.collect { data ->
//                try {
//                    //Log.d(TAG, "SHR: ${data!!.data.data[0].name}")
//                    withContext(Dispatchers.Main) {
//                        categoriesAdapter =
//                            CategoriesAdapter(this@CategoryProducts, data!!.data.data)
//                        recyclerView.adapter = categoriesAdapter
//                        facebookShimmerFactory.stopShimmer()
//                    }
//                } catch (e: Exception) {
//                    Log.d(TAG, "SHR: ${e.message}")
//                }
//            }
//        }
//    }

    private fun assignVariables() {
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        recyclerView = binding.categoryProductsRecycler
        facebookShimmerFactory = FacebookShimmerFactory(binding.shimmerFrameLayout)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}