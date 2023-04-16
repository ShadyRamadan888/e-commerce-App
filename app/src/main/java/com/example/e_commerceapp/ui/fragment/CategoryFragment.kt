package com.example.e_commerceapp.ui.fragment

import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CategoryFragment : Fragment() {


    private lateinit var clothesImg: ImageView
    private lateinit var sportsImg: ImageView
    private lateinit var coronaImg: ImageView
    private lateinit var devicesImg: ImageView
    private lateinit var lightingImg: ImageView
    private lateinit var viewModel: ProductsViewModel
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_category, container, false)

        assignVariables(view)

        getCategories()
        setUpToolBar()

        return view
    }

    fun getCategories() {

        viewModel.getHome()
        lifecycleScope.launch(Dispatchers.IO) {

            viewModel.dataStateFlow.collect { dataState ->
                try {
                    withContext(Dispatchers.Main) {
                        Glide.with(requireContext())
                            .load(dataState.categories?.data?.data?.get(0)?.image).into(devicesImg)
                        Glide.with(requireContext())
                            .load(dataState.categories?.data?.data?.get(1)?.image).into(coronaImg)
                        Glide.with(requireContext())
                            .load(dataState.categories?.data?.data?.get(2)?.image).into(sportsImg)
                        Glide.with(requireContext())
                            .load(dataState.categories?.data?.data?.get(3)?.image).into(lightingImg)
                        Glide.with(requireContext())
                            .load(dataState.categories?.data?.data?.get(4)?.image).into(clothesImg)

                    }
                } catch (e: Exception) {

                }
            }
        }
    }

    fun assignVariables(view: View) {
        toolbar = view.findViewById<Toolbar>(R.id.catToolBar) as androidx.appcompat.widget.Toolbar
        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        clothesImg = view.findViewById(R.id.clothesCategoryImage)
        sportsImg = view.findViewById(R.id.sportsCategoryImage)
        coronaImg = view.findViewById(R.id.coronaCategoryImage)
        lightingImg = view.findViewById(R.id.lightingCategoryImage)
        devicesImg = view.findViewById(R.id.devicesCategoryImage)
    }
    fun setUpToolBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app))
    }

}