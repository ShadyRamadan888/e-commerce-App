package com.example.e_commerceapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.balysv.materialripple.MaterialRippleLayout
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentCategoryBinding
import com.example.e_commerceapp.ui.activties.CategoryProducts
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


class CategoryFragment : Fragment() {

    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var imagesList: List<ImageView>
    private val TAG = "CategoryFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_category, container, false)

        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]

        binding = FragmentCategoryBinding.bind(view)

        setUpToolBar()

        clickCategory(binding.sportsCategory, 42,"Sports")
        clickCategory(binding.clothesCategory, 46,"Clothes")
        clickCategory(binding.coronaCategory, 43,"Prevent Corona")
        clickCategory(binding.lightingCategory, 40,"Lighting")
        clickCategory(binding.electronicCategory, 44,"Electronics")
        //*************//*************************
        imagesList = listOf<ImageView>(
            binding.devicesCategoryImage,
            binding.coronaCategoryImage,
            binding.sportsCategoryImage,
            binding.lightingCategoryImage,
            binding.clothesCategoryImage
        )
        getCategories(imagesList)

        return view
    }


    private fun getCategories(images: List<ImageView>) {

        viewModel.getHome()
        lifecycleScope.launch(Dispatchers.IO) {

            viewModel.dataStateFlow.collect { dataState ->
                try {
                    withContext(Dispatchers.Main) {
                        for ((counter, i) in images.withIndex()) {
                            Glide.with(requireContext())
                                .load(dataState.categories?.data?.data?.get(counter)?.image)
                                .into(i)
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "getCategories: ${e.message}")
                }
            }
        }
    }

    private fun clickCategory(materialRippleLayout: MaterialRippleLayout, id: Int,categoryName:String) {
        materialRippleLayout.setOnClickListener {
            val intent = Intent(requireContext(), CategoryProducts::class.java)
            intent.putExtra("catId", id)
            intent.putExtra("CatName",categoryName)
            startActivity(intent)
        }
    }

    private fun setUpToolBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.catToolBar)
        binding.catToolBar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app))
    }

}