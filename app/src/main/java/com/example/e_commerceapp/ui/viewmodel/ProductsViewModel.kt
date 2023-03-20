package com.example.e_commerceapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ProductRoot
import com.example.domain.usecase.GetMainProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject  constructor(
    private val getProductsUseCase: GetMainProducts
) : ViewModel() {


    private val _liveData:MutableStateFlow<ProductRoot?> = MutableStateFlow(null)
    val liveData:StateFlow<ProductRoot?> = _liveData

    private val TAG = "ProductsViewModel"

    fun getMainProducts() {
        viewModelScope.launch {

            try {
                _liveData.emit(getProductsUseCase())
            } catch (e: Exception) {
                Log.e(TAG, "SHR: ${e.message}")
            }
        }
    }

}