package com.jop.marketjp.ui.screens.home.view.model

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jop.marketjp.repository.category.CategoryImp
import com.jop.marketjp.repository.products.ProductImp
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
    application: Application
): BaseViewModel(application){

    init {
        initViewState(HomeViewState())
        getCategoryAll()
        getProductsAll()
    }

    fun onEvent(event: HomeViewEvent){

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
        productImp.getProductAll(state.searchProduct).collect {
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

    private fun updateIsLoading(isLoading: Boolean){
        val state: HomeViewState = currentViewState()
        updateViewState(state.copy(isLoading = isLoading))
    }
}