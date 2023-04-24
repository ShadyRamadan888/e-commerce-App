package com.example.e_commerceapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentFavouritesBinding
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavouritesFragment : Fragment() {

    private  val TAG = "FavouritesFragment"
    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_favourites, container, false)

        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        binding = FragmentFavouritesBinding.bind(view)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getAllFav()
    }

    private fun getAllFav(){
        viewModel.getAllFavoriteItems()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                viewModel.getAllFavStateFlow.collect{

                    withContext(Dispatchers.Main){
                        try {
                            //Log.d(TAG, "SHR_All_Fav: ${it!!.data[0].data.name}")
                        }catch (e:Exception){
                            Log.d(TAG, "SHR_All_Fav_E: ${e.message}")
                        }
                    }
                }
            }catch (e:Exception){
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }
}