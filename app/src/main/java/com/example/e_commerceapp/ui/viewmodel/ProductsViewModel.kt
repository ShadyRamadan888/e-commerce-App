package com.example.e_commerceapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.Root
import com.example.domain.usecase.CategoriesUseCase
import com.example.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getHomeData: HomeUseCase,
    private val getCategories: CategoriesUseCase,
) : ViewModel() {

    private val TAG = "ProductsViewModel"

    data class DataState(val home: Flow<Root>?, val categories: Root?, val error: Throwable?)

    private val _dataStateFlow = MutableStateFlow<DataState>(DataState(null, null, null))
    val dataStateFlow: StateFlow<DataState> = _dataStateFlow

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getHome() {
        coroutineScope.launch {
            try {
                val home = getHomeData()
                val categories = getCategories()
                _dataStateFlow.emit(DataState(home, categories, null))
            } catch (e: Throwable) {
                _dataStateFlow.emit(DataState(null, null, e))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //To avoid memory leak
        coroutineScope.cancel()
    }
}