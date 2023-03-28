package com.example.e_commerceapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.utils.CheckInternetConnection
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.LoadingDialog
import com.example.e_commerceapp.ui.adapters.BannerAdapter
import com.example.e_commerceapp.ui.adapters.CategoriesAdapter
import com.example.e_commerceapp.ui.adapters.HomeProductAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {


    lateinit var homeProductAdapter: HomeProductAdapter
    lateinit var homeProductRecyclerView: RecyclerView
    lateinit var bannerAdapter: BannerAdapter
    lateinit var bannerSlider: SliderView
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var categoryRecyclerView: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: ProductsViewModel
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private val TAG = "HomeFragment"

    //Connection
    private lateinit var checkInternetConnection: CheckInternetConnection


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)



        assignVariables(view)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callNetworkConnection()

    }

    private fun assignVariables(view: View) {

        loadingDialog = LoadingDialog(requireActivity())
        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        homeProductRecyclerView = view.findViewById(R.id.homeProductRecyclerView)
        homeProductRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        bannerSlider = view.findViewById(R.id.bannerHome)
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout)



        //imageView = view.findViewById(R.id.imageView)
        //textView = view.findViewById(R.id.textView)
    }

    private fun callNetworkConnection() {


        checkInternetConnection = CheckInternetConnection(requireActivity().application)

        checkInternetConnection.observe(requireActivity()) { isConnected ->
            if (isConnected) {
                //imageView.setImageResource(0)
                //textView.setText("")
                getHomeProducts()
                getCategories()
            } else {
                //imageView.setImageResource(R.drawable.wifi_disconnected)
                //textView.setText("Network Disconnected")
                //textView.setTextColor(Color.parseColor("#F44336"))
            }
        }
    }


    fun getHomeProducts() {

        viewModel.getHome()
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.homeStateFlow.collect {
                try {
                    homeProductAdapter = HomeProductAdapter(it?.data?.products!!, requireContext())
                    bannerAdapter = BannerAdapter(it.data.banners, requireContext())
                    withContext(Dispatchers.Main) {
                        shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE
                        bannerSlider.visibility = View.VISIBLE
                        homeProductRecyclerView.visibility = View.VISIBLE
                        bannerSlider.setSliderAdapter(bannerAdapter)
                        homeProductRecyclerView.adapter = homeProductAdapter
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "SHR: ${e.message}")
                }
            }
        }
    }


    fun getCategories() {

        viewModel.getHome()
        lifecycleScope.launch(Dispatchers.IO) {

            viewModel.categoryStateFlow.collect {

                try {
                    categoriesAdapter = CategoriesAdapter(it?.data?.data!!)
                    withContext(Dispatchers.Main) {
                        categoryRecyclerView.adapter = categoriesAdapter
                        categoryRecyclerView.visibility = View.VISIBLE
                        //Log.d(TAG, "SHR: ${it?.data?.data!![0].name}")
                    }
                } catch (e: Exception) {

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmer()
        super.onPause()
    }
}