package com.jop.marketjp.ui.screens.shopping.view.model

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.jop.marketjp.repository.shopping.cart.local.LocalCartShoppingImp
import com.jop.marketjp.ui.screens.shopping.view.event.ShoppingCartViewEvent
import com.jop.marketjp.ui.screens.shopping.view.state.ShoppingCartViewState
import com.jop.marketjp.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val localCartShoppingImp: LocalCartShoppingImp,
    application: Application
): BaseViewModel(application) {
    init {
        initViewState(ShoppingCartViewState())
        getShoppingCart()
    }

    fun onEvent(event: ShoppingCartViewEvent){
        when(event){
            is ShoppingCartViewEvent.UpdateChangeAmount -> updateAmount(id = event.id, newAmount = event.newAmount)
            is ShoppingCartViewEvent.DeleteCartById -> deleteCartById(event.id)
        }
    }

    private fun getShoppingCart() = viewModelScope.launch {
        localCartShoppingImp.getAllCartShopping().collect { carts ->
            val state: ShoppingCartViewState = currentViewState()
            updateViewState(state.copy(listShoppingCart = carts))
        }
    }
    private fun updateAmount(id: Int, newAmount: Int) = viewModelScope.launch {
        localCartShoppingImp.updateAmount(id, newAmount)
    }
    private fun deleteCartById(id: Int) = viewModelScope.launch {
        localCartShoppingImp.deleteCartById(id)
    }
}