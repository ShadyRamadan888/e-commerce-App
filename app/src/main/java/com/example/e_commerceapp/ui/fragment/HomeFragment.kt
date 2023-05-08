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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerceapp.utils.CheckInternetConnection
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentHomeBinding
import com.example.e_commerceapp.di.DaggerMyComponent
import com.example.e_commerceapp.di.MyComponent
import com.example.e_commerceapp.ui.adapters.BannerAdapter
import com.example.e_commerceapp.ui.adapters.HomeProductAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.example.e_commerceapp.utils.FacebookShimmerFactory
import kotlinx.coroutines.*
import javax.inject.Inject


class HomeFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var homeProductAdapter: HomeProductAdapter
    @Inject
    lateinit var bannerAdapter: BannerAdapter
    private lateinit var viewModel: ProductsViewModel
    lateinit var facebookShimmerFactory: FacebookShimmerFactory
    private val homeDataScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var binding: FragmentHomeBinding
    private val TAG = "HomeFragment"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)


        binding = FragmentHomeBinding.bind(view)
        assignVariables(view)
        setUpToolBar()

        //DI
        val component: MyComponent = DaggerMyComponent.create()
        component.injectHomeFragment(this)

        getHomeData()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //callNetworkConnection()

    }

    private fun assignVariables(view: View) {

        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        binding.homeProductRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.homeProductRecyclerView.setHasFixedSize(true)
        facebookShimmerFactory = FacebookShimmerFactory(binding.shimmerFrameLayout)

    }

//    private fun callNetworkConnection() {
//
//
//        checkInternetConnection = CheckInternetConnection(requireActivity().application)
//
//        checkInternetConnection.observe(requireActivity()) { isConnected ->
//            if (isConnected) {
//
//            } else {
//                //imageView.setImageResource(R.drawable.wifi_disconnected)
//                //textView.setText("Network Disconnected")
//                //textView.setTextColor(Color.parseColor("#F44336"))
//            }
//        }
//    }

    private fun setUpToolBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolBar)
        binding.toolBar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app))
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_search_24)
        binding.toolBar.setNavigationOnClickListener {
            // Handle click on the icon
            Toast.makeText(requireActivity(), "Search", Toast.LENGTH_LONG).show()
        }
    }


    private fun getHomeData() {
        facebookShimmerFactory.startShimmer()
        viewModel.getHome()
        lifecycleScope.launch {
            try {
                viewModel.viewStateFlow.collect { viewState ->
                    withContext(Dispatchers.Main) {
                        try {
                            viewState.home!!.collect { homeData ->
                                homeProductAdapter.updateData(homeData.data.products)
                                bannerAdapter.updateData(homeData.data.banners)
                                updateUI()
                                facebookShimmerFactory.stopShimmer()
                            }
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

    private fun updateUI() {
        binding.homeProductRecyclerView.adapter = homeProductAdapter
        binding.bannerHome.setSliderAdapter(bannerAdapter)
    }



    override fun onDestroyView() {
        homeDataScope.cancel()
        super.onDestroyView()
    }

}