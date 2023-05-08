package com.example.e_commerceapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentFavouritesBinding
import com.example.e_commerceapp.ui.adapters.FavoritesAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.example.e_commerceapp.utils.SwipeFavToDeleteCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavouritesFragment : Fragment() {

    private val TAG = "FavouritesFragment"
    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var swipeFavToDeleteCallback: SwipeFavToDeleteCallback
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_favourites, container, false)

        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        binding = FragmentFavouritesBinding.bind(view)

        binding.favRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favRecycler.setHasFixedSize(true)

        setUpToolBar()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllFav()

    }


    private fun getAllFav() {
        viewModel.getAllFavorites(requireContext())
        lifecycleScope.launch {
            try {
                viewModel.viewStateFlow.collect{

                    try {
                        withContext(Dispatchers.Main){
                            favoritesAdapter = FavoritesAdapter(requireContext())
                            favoritesAdapter.setList(it.favorites!!)
                            binding.favRecycler.adapter = favoritesAdapter
                            swipeFavToDeleteCallback = SwipeFavToDeleteCallback(favoritesAdapter)
                            itemTouchHelper = ItemTouchHelper(swipeFavToDeleteCallback)
                            itemTouchHelper.attachToRecyclerView(binding.favRecycler)
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "SHR: ${e.message}")
                    }

                }
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }

    private fun setUpToolBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolBar)
        binding.toolBar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app))
    }

//    private fun getAllFav() {
//        viewModel.getAllFavorites(requireContext())
//        lifecycleScope.launch {
//            try {
//                viewModel.favoriteStateFlow.collect { favorites ->
//                    updateFavoritesList(favorites)
//                }
//            } catch (e: Exception) {
//                Log.d(TAG, "SHR: ${e.message}")
//            }
//        }
//    }
//
//    private fun updateFavoritesList(favorites: List<FavoritesEntity>?) {
//        try {
//            favoritesAdapter = FavoritesAdapter(requireContext())
//            favoritesAdapter.setList(favorites!!)
//            binding.favRecycler.adapter = favoritesAdapter
//            swipeToDeleteCallback = SwipeToDeleteCallback(favoritesAdapter)
//            itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
//            itemTouchHelper.attachToRecyclerView(binding.favRecycler)
//        } catch (e: Exception) {
//            Log.d(TAG, "SHR: ${e.message}")
//        }
//    }
//

}