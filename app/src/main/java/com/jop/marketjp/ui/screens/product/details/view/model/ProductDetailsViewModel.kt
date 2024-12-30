package com.jop.marketjp.ui.screens.product.details.view.model

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.jop.domain.models.product.ProductResponse
import com.jop.marketjp.App
import com.jop.marketjp.repository.products.ProductImp
import com.jop.marketjp.ui.navigation.Params
import com.jop.marketjp.ui.screens.home.view.state.HomeViewState
import com.jop.marketjp.ui.screens.product.details.view.event.ProductDetailsViewEvent
import com.jop.marketjp.ui.screens.product.details.view.state.ProductDetailsViewState
import com.jop.marketjp.ui.utils.snackbar.IcoSnackbar
import com.jop.marketjp.ui.viewmodel.BaseViewModel
import com.jop.webs.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productImp: ProductImp,
    application: Application
): BaseViewModel(application) {

    init {
        initViewState(ProductDetailsViewState())
        getOneProduct()
    }

    fun onEvent(event: ProductDetailsViewEvent){
        when(event){
            is ProductDetailsViewEvent.AddCart -> addCartShopping(event.value)
        }
    }
    private fun addCartShopping(value: ProductResponse) = viewModelScope.launch {

    }
    private fun getOneProduct() = viewModelScope.launch {
        val productId = App.navigation[Params.PRODUCT_ID].castValue<Int>() ?: 0
        productImp.getOneProduct(productId = productId).collect {
            when(it){
                is NetworkResult.Loading -> updateIsLoading(true)
                is NetworkResult.Error -> {
                    updateIsLoading(false)
                    showSnackbar(IcoSnackbar.ERROR, "Error")
                }
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        val stateUp: ProductDetailsViewState = currentViewState()
                        updateViewState(stateUp.copy(productDetail = response))
                    }
                    updateIsLoading(false)
                }
            }
        }
    }

    private fun updateIsLoading(isLoading: Boolean){
        val state: ProductDetailsViewState = currentViewState()
        updateViewState(state.copy(isLoading = isLoading))
    }
}