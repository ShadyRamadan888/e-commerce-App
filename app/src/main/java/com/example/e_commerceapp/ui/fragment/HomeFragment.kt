package com.example.e_commerceapp.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.utils.CheckInternetConnection
import com.example.e_commerceapp.R
import com.example.e_commerceapp.utils.LoadingDialog
import com.example.e_commerceapp.ui.adapters.BannerAdapter
import com.example.e_commerceapp.ui.adapters.HomeProductAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import com.example.e_commerceapp.utils.FacebookShimmerFactory
import com.facebook.shimmer.ShimmerFrameLayout
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn


class HomeFragment : Fragment() {


    private lateinit var homeProductAdapter: HomeProductAdapter
    private lateinit var homeProductRecyclerView: RecyclerView
    lateinit var bannerAdapter: BannerAdapter
    private lateinit var bannerSlider: SliderView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var facebookShimmerFactory: FacebookShimmerFactory
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private val homeDataScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: ProductsViewModel
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var builder: AlertDialog.Builder

    lateinit var dialog: AlertDialog
    private val TAG = "HomeFragment"

    //Connection
    private lateinit var checkInternetConnection: CheckInternetConnection


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)



        assignVariables(view)
        //alertDialog.create()
        //alertDialog.setTitle("Connection")

        setUpToolBar()
        getHomeData()
        //callNetworkConnection()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun assignVariables(view: View) {

        loadingDialog = LoadingDialog(requireActivity())
        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        homeProductRecyclerView = view.findViewById(R.id.homeProductRecyclerView)
        homeProductRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        homeProductRecyclerView.setHasFixedSize(true)
        bannerSlider = view.findViewById(R.id.bannerHome)
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout)
        facebookShimmerFactory = FacebookShimmerFactory(shimmerFrameLayout)
        toolbar = view.findViewById<Toolbar>(R.id.toolBar) as androidx.appcompat.widget.Toolbar

        if (!::homeProductAdapter.isInitialized) {
            homeProductAdapter = HomeProductAdapter(requireContext())
        }

        bannerAdapter = BannerAdapter(requireContext())
        builder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
        dialog = builder.create()

        //imageView = view.findViewById(R.id.imageView)
        //textView = view.findViewById(R.id.textView)
    }

    private fun callNetworkConnection() {


        checkInternetConnection = CheckInternetConnection(requireActivity().application)

        checkInternetConnection.observe(requireActivity()) { isConnected ->
            if (isConnected) {
                //imageView.setImageResource(0)
                //textView.setText("")
                //getHomeData()
                //getHomeData()
                //dialog.dismiss()
                //getCategories()
            } else {
                //imageView.setImageResource(R.drawable.wifi_disconnected)
                //textView.setText("Network Disconnected")
                dialog.show()
                //textView.setTextColor(Color.parseColor("#F44336"))
            }
        }
    }

    private fun setUpToolBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app))
        toolbar.setNavigationIcon(R.drawable.ic_baseline_search_24)
        toolbar.setNavigationOnClickListener {
            // Handle click on the icon
            Toast.makeText(requireActivity(), "Search", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()

        //Check if the recyclerView has data or not
        //To prevent reloading data (the user will miss the product he stop on)
        if (homeProductAdapter.itemCount == 0) {
            getHomeData()
        }

    }

    private fun getHomeData() {
        viewModel.getHome()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataStateFlow
                .catch { e ->
                    Log.d(TAG, "SHR: ${e.message}")
                }
                .flowOn(Dispatchers.IO)
                .collect { dataState ->
                    dataState.home?.let { homeState ->
                        try {
                            homeState.collect { home ->
                                homeProductAdapter.updateData(home.data.products)
                                bannerAdapter.updateData(home.data.banners)
                            }
                            //to check if the fragment is in the started state before updating the UI
                            if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                                updateUI()
                                facebookShimmerFactory.stopShimmer()
                            }
                        } catch (e: CancellationException) {
                            Log.d(TAG, "SHR: ${e.message}")
                        }
                    }
                }
        }
    }

    private fun updateUI() {
        homeProductRecyclerView.visibility = View.VISIBLE
        homeProductRecyclerView.adapter = homeProductAdapter
        bannerSlider.visibility = View.VISIBLE
        bannerSlider.setSliderAdapter(bannerAdapter)
    }

    override fun onResume() {
        super.onResume()
        facebookShimmerFactory.startShimmer()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        homeDataScope.cancel()
        super.onDestroyView()
    }

}