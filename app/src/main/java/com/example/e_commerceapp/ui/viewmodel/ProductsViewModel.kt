package com.example.e_commerceapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Data
import com.example.domain.model.Root
import com.example.domain.usecase.GetCategoriesNames
import com.example.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getHomeData:HomeUseCase,
    private val getCategories:GetCategoriesNames
) : ViewModel() {

    private val TAG = "ProductsViewModel"

    private val _mutableHomeStateFlow: MutableStateFlow<Root?> = MutableStateFlow(null)
    val homeStateFlow:StateFlow<Root?> = _mutableHomeStateFlow

    private val _mutableCategoriesStateFlow: MutableStateFlow<Root?> = MutableStateFlow(null)
    val categoryStateFlow:StateFlow<Root?> = _mutableCategoriesStateFlow
    fun getHome() {
        viewModelScope.launch {
            try {
                _mutableHomeStateFlow.emit(getHomeData())
                _mutableCategoriesStateFlow.emit(getCategories())
            }catch (e:Exception){
                Log.d(TAG, "SHR: ${e.message}")
            }
        }
    }

}