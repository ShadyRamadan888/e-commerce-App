package com.example.e_commerceapp.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.fav_room.FavoriteDatabase
import com.example.domain.model.*
import com.example.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getHomeData: HomeUseCase,
    private val getCategories: CategoriesUseCase,
    private val getProductDetails: GetProductDetailsUseCase,
    private val getCategoryProducts: CategoryProductsUseCase,
    private val addRemoveCart: AddCartUseCase,
    private val getAllFav: GetAllFavoritesUseCase,
    private val getAllCarts: GetAllCartsUseCase
) : ViewModel() {

    private val TAG = "ProductsViewModel"


    data class ProductsViewState(
        val home: Flow<Root>? = null,
        val categories: Flow<Root>? = null,
        val product: ProductRoot? = null,
        val catProduct: CatProduct? = null,
        val carts: List<CartEntity>? = null,
        val favorites: List<FavoritesEntity>? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )

    private val _viewStateFlow = MutableStateFlow(ProductsViewState())
    val viewStateFlow: StateFlow<ProductsViewState> = _viewStateFlow


    fun getHome() {
        viewModelScope.launch {
            try {
                _viewStateFlow.value = ProductsViewState(
                    home = getHomeData(),
                    categories = getCategories(),
                    isLoading = true
                )
            } catch (e: Throwable) {
                _viewStateFlow.value = ProductsViewState(error = e)
            }
        }
    }


    fun getProduct(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val product = getProductDetails(id)
                _viewStateFlow.value = ProductsViewState(
                    product = product,
                    isLoading = true
                )
            } catch (e: Throwable) {
                _viewStateFlow.value = ProductsViewState(
                    error = e
                )
            }
        }
    }

    fun categoryProducts(category_id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val product = getCategoryProducts(category_id)
                _viewStateFlow.value = ProductsViewState(
                    catProduct = product,
                    isLoading = true
                )
            } catch (e: Throwable) {
                _viewStateFlow.value = ProductsViewState(
                    error = e
                )
            }
        }
    }


    fun allCarts(context: Context) {
        viewModelScope.launch {
            try {
                val carts = getAllCarts(context)
                _viewStateFlow.value = ProductsViewState(
                    carts = carts,
                    isLoading = true
                )
            } catch (e: Exception) {
                _viewStateFlow.value = ProductsViewState(
                    error = e
                )
            }
        }
    }


    fun getAllFavorites(context: Context) {
        viewModelScope.launch {
            try {
                val data = getAllFav(context)
                withContext(Dispatchers.Main) {
                    _viewStateFlow.value = ProductsViewState(
                        favorites = data,
                        isLoading = true
                    )
                }
            } catch (e: Exception) {
                _viewStateFlow.value = ProductsViewState(
                    error = e
                )
            }
        }
    }

}