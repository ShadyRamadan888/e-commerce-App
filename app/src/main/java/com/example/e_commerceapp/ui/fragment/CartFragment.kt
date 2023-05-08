package com.example.e_commerceapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentCartBinding
import com.example.e_commerceapp.ui.adapters.CartsAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.example.e_commerceapp.utils.SwipeCartToDeleteCallback
import com.example.e_commerceapp.utils.SwipeFavToDeleteCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CartFragment : Fragment() {

    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartsAdapter: CartsAdapter
    private lateinit var swipeCartToDeleteCallback: SwipeCartToDeleteCallback
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val TAG = "CartFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        binding = FragmentCartBinding.bind(view)

        binding.cartsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartsRecyclerView.hasFixedSize()

        cartsAdapter = CartsAdapter()

        getAllCarts()
    }

    @SuppressLint("SetTextI18n")
    private fun getAllCarts() {
        viewModel.allCarts(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                viewModel.viewStateFlow.collect { mainData ->
                    try {
                        withContext(Dispatchers.Main){
                            cartsAdapter.updateList(mainData.carts!!)
                            binding.cartsRecyclerView.adapter = cartsAdapter
                            swipeCartToDeleteCallback = SwipeCartToDeleteCallback(cartsAdapter)
                            itemTouchHelper = ItemTouchHelper(swipeCartToDeleteCallback)
                            itemTouchHelper.attachToRecyclerView(binding.cartsRecyclerView)
                            var total = 0.0
                            for (i in mainData.carts){
                                total += i.price
                            }
                            binding.totalPrice.text = "$ "+total.toInt().toString()
                        }
                    }catch (e:Exception){
                        Log.d(TAG, "SHR: ${e.message}")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }

}