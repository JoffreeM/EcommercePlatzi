package com.jop.marketjp.ui.screens.category.view.model

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.jop.domain.models.product.ProductResponse
import com.jop.marketjp.App
import com.jop.marketjp.R
import com.jop.marketjp.repository.products.ProductImp
import com.jop.marketjp.repository.shopping.cart.local.LocalCartShoppingImp
import com.jop.marketjp.ui.navigation.Params
import com.jop.marketjp.ui.screens.category.view.event.CategoryViewEvent
import com.jop.marketjp.ui.screens.category.view.state.CategoryViewState
import com.jop.marketjp.ui.utils.snackbar.IcoSnackbar
import com.jop.marketjp.ui.viewmodel.BaseViewModel
import com.jop.webs.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val productImp: ProductImp,
    private val localCartShoppingImp: LocalCartShoppingImp,
    application: Application
): BaseViewModel(application) {

    init {
        initViewState(CategoryViewState())
        getProductsOfCategory()
    }

    fun onEvent(event: CategoryViewEvent){
        when(event){
            is CategoryViewEvent.UpdateSearchProduct -> updateSearchProduct(event.value)
            is CategoryViewEvent.UpdatePriceMax -> updatePriceMax(event.value)
            is CategoryViewEvent.UpdatePriceMin -> updatePriceMin(event.value)
            is CategoryViewEvent.SearchProduct -> filterDataLocale()
            is CategoryViewEvent.AddCartShopping -> addCartShopping(event.value)
            is CategoryViewEvent.RefreshProduct -> getProductsOfCategory()
        }
    }

    private fun addCartShopping(value: ProductResponse) = viewModelScope.launch {
        localCartShoppingImp.insertCartShopping(value.onEntity())
        showSnackbar(IcoSnackbar.CORRECT, getString(R.string.product_add_cart_toast))
    }

    private fun filterDataLocale(){
        val state: CategoryViewState = currentViewState()

        val newListProduct = state.listProducts.filter { product ->
            // Filtrar por búsqueda de producto
            val matchesSearch = state.searchProduct.isBlank() ||
                    product.title.contains(state.searchProduct, ignoreCase = true)

            // Filtrar por precio máximo
            val matchesPriceMax = state.priceMax == null || product.price <= state.priceMax

            // Filtrar por precio mínimo
            val matchesPriceMin = state.priceMin == null || product.price >= state.priceMin

            // Devolver true si cumple todas las condiciones
            matchesSearch && matchesPriceMax && matchesPriceMin
        }

        updateViewState(state.copy(
            listProducts = newListProduct
        ))
    }
    private fun getProductsOfCategory() = viewModelScope.launch {
        val categoryId = App.navigation[Params.CATEGORY_ID].castValue<Int>() ?: 0
        productImp.getProductsOfCategory(categoryId = categoryId).collect {
            when(it){
                is NetworkResult.Loading -> updateIsLoading(true)
                is NetworkResult.Error -> {
                    updateIsLoading(false)
                    showSnackbar(IcoSnackbar.ERROR, "Error")
                }
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        val state: CategoryViewState = currentViewState()
                        updateViewState(state.copy(
                            listProducts = response,
                            nameCategory = response.first().category.name,
                            categoryImage = response.first().category.image
                        ))
                    }
                    updateIsLoading(false)
                }
            }
        }
    }
    private fun updatePriceMax(value: Int? = null){
        val state: CategoryViewState = currentViewState()
        updateViewState(state.copy(priceMax = value))
    }
    private fun updatePriceMin(value: Int? = null){
        val state: CategoryViewState = currentViewState()
        updateViewState(state.copy(priceMin = value))
    }
    private fun updateSearchProduct(value: String){
        val state: CategoryViewState = currentViewState()
        updateViewState(state.copy(searchProduct = value))
    }
    private fun updateIsLoading(isLoading: Boolean){
        val state: CategoryViewState = currentViewState()
        updateViewState(state.copy(isLoading = isLoading))
    }
}