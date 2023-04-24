package com.example.e_commerceapp.ui.viewmodel

import androidx.lifecycle.ViewModel
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
    private val addFavById: AddFavoriteUseCase,
    private val getAllFav:GetAllFavoritesUseCase
) : ViewModel() {

    private val TAG = "ProductsViewModel"

    data class DataState(
        val home: Flow<Root>?,
        val categories: Root?,
        val error: Throwable?
    )

    private val _dataStateFlow = MutableStateFlow<DataState>(DataState(null, null, null))
    val dataStateFlow: StateFlow<DataState> = _dataStateFlow

    private val _productStateFlow = MutableStateFlow<ProductRoot?>(null)
    val productStateFlow: StateFlow<ProductRoot?> = _productStateFlow

    private val _catProductStateFlow = MutableStateFlow<CatProduct?>(null)
    val catProductStateFlow: StateFlow<CatProduct?> = _catProductStateFlow


    private val _addFavStateFlow = MutableStateFlow<AddRemoveFavRes?>(null)
    val addFavStateFlow:StateFlow<AddRemoveFavRes?> = _addFavStateFlow

    private val _getAllFavStateFlow = MutableStateFlow<GetAllFav?>(null)
    val getAllFavStateFlow:StateFlow<GetAllFav?> = _getAllFavStateFlow

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


    fun getProduct(id:Int) {

        coroutineScope.launch(Dispatchers.IO) {
            try {
                val product = getProductDetails(id)
                _productStateFlow.emit(product)
            } catch (e: Throwable) {
                _productStateFlow.emit(null)
            }
        }
    }

    fun categoryProducts(category_id:Int){
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val product = getCategoryProducts(category_id)
                _catProductStateFlow.emit(product)
            } catch (e: Throwable) {
                _catProductStateFlow.emit(null)
            }
        }
    }

    fun addFavoriteById(fav:AddRemoveFavOrCart){
        coroutineScope.launch {
            try {
                val addFavItem = addFavById(fav)
                _addFavStateFlow.emit(addFavItem)
            }catch (e:Exception){
                _addFavStateFlow.emit(null)
            }
        }
    }

    fun getAllFavoriteItems(){

        coroutineScope.launch {
            try {
                val data = getAllFav()
                _getAllFavStateFlow.emit(data)
            }catch (e:Exception){
                _getAllFavStateFlow.emit(null)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        //To avoid memory leak
        coroutineScope.cancel()
    }
}