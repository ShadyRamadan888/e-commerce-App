package com.example.e_commerceapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerceapp.utils.CheckInternetConnection
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.LoadingDialog
import com.example.e_commerceapp.ui.adapters.ProductAdapter
import com.example.e_commerceapp.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {


    private lateinit var grid_home: GridView
    lateinit var productAdapter: ProductAdapter
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: ProductsViewModel
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView


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
        grid_home = view.findViewById(R.id.gride_home)
        productAdapter = ProductAdapter()
        loadingDialog = LoadingDialog(requireActivity())
        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        imageView = view.findViewById(R.id.imageView)
        textView = view.findViewById(R.id.textView)
    }

    private fun callNetworkConnection() {


        checkInternetConnection = CheckInternetConnection(requireActivity().application)

        checkInternetConnection.observe(requireActivity()) { isConnected ->
            if (isConnected) {
                imageView.setImageResource(0)
                textView.setText("")
                getMainProducts()
            } else {
                imageView.setImageResource(R.drawable.wifi_disconnected)
                textView.setText("Network Disconnected")
                textView.setTextColor(Color.parseColor("#F44336"))
            }
        }
    }

    private fun getMainProducts() {

        viewModel.getMainProducts()
        loadingDialog.startLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.liveData.collect {
                try {
                    productAdapter.submitList(it?.products)
                    //Execute these in main thread
                    withContext(Dispatchers.Main) {
                        delay(500)
                        grid_home.adapter = productAdapter
                        loadingDialog.isDismiss()
                    }
                } catch (_: Exception) {}
            }
        }
    }


}