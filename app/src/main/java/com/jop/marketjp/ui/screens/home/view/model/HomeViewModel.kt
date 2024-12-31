package com.jop.marketjp.ui.screens.home.view.model

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jop.domain.models.product.ProductResponse
import com.jop.marketjp.R
import com.jop.marketjp.repository.category.CategoryImp
import com.jop.marketjp.repository.products.ProductImp
import com.jop.marketjp.repository.shopping.cart.local.LocalCartShoppingImp
import com.jop.marketjp.ui.screens.home.view.event.HomeViewEvent
import com.jop.marketjp.ui.screens.home.view.state.HomeViewState
import com.jop.marketjp.ui.utils.snackbar.IcoSnackbar
import com.jop.marketjp.ui.viewmodel.BaseViewModel
import com.jop.webs.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryImp: CategoryImp,
    private val productImp: ProductImp,
    private val localCartShoppingImp: LocalCartShoppingImp,
    application: Application
): BaseViewModel(application){

    init {
        initViewState(HomeViewState())
        getCategoryAll()
        getProductsAll()
    }

    fun onEvent(event: HomeViewEvent){
        when(event){
            is HomeViewEvent.UpdateSearchProduct -> updateSearchProduct(event.value)
            is HomeViewEvent.UpdateCategoryId -> updateCategoryId(event.value)
            is HomeViewEvent.UpdatePriceMax -> updatePriceMax(event.value)
            is HomeViewEvent.UpdatePriceMin -> updatePriceMin(event.value)
            is HomeViewEvent.AddCartShopping -> addCartShopping(event.value)
            is HomeViewEvent.SearchProduct -> getProductsAll()
        }
    }
    private fun addCartShopping(value: ProductResponse) = viewModelScope.launch {
        localCartShoppingImp.insertCartShopping(value.onEntity())
        showSnackbar(IcoSnackbar.CORRECT, getString(R.string.product_add_cart_toast))
    }
    private fun getCategoryAll() = viewModelScope.launch {
        categoryImp.getCategoryAll().collect {
            when(it){
                is NetworkResult.Loading -> updateIsLoading(true)
                is NetworkResult.Error -> {
                    updateIsLoading(false)
                    showSnackbar(IcoSnackbar.ERROR, "Error")
                }
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        val state: HomeViewState = currentViewState()
                        updateViewState(state.copy(listCategory = response))
                    }
                    updateIsLoading(false)
                }
            }
        }
    }

    private fun getProductsAll() = viewModelScope.launch {
        val state: HomeViewState = currentViewState()
        productImp.getProductAll(
            nameProduct = state.searchProduct,
            priceMin = state.priceMin,
            priceMax = state.priceMax,
            categoryId = state.categoryId
        ).collect {
            when(it){
                is NetworkResult.Loading -> updateIsLoading(true)
                is NetworkResult.Error -> {
                    updateIsLoading(false)
                    showSnackbar(IcoSnackbar.ERROR, "Error")
                }
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        val stateUp: HomeViewState = currentViewState()
                        updateViewState(stateUp.copy(listProducts = response))
                    }
                    updateIsLoading(false)
                }
            }
        }
    }
    private fun updateCategoryId(value: Int? = null){
        val state: HomeViewState = currentViewState()
        updateViewState(state.copy(categoryId = value))
    }
    private fun updatePriceMax(value: Int? = null){
        val state: HomeViewState = currentViewState()
        updateViewState(state.copy(priceMax = value))
    }
    private fun updatePriceMin(value: Int? = null){
        val state: HomeViewState = currentViewState()
        updateViewState(state.copy(priceMin = value))
    }
    private fun updateSearchProduct(value: String){
        val state: HomeViewState = currentViewState()
        updateViewState(state.copy(searchProduct = value))
    }
    private fun updateIsLoading(isLoading: Boolean){
        val state: HomeViewState = currentViewState()
        updateViewState(state.copy(isLoading = isLoading))
    }
}