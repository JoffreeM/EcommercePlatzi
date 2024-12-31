package com.jop.marketjp.ui.navigation.notification

import android.app.Application
import com.jop.marketjp.repository.shopping.cart.local.LocalCartShoppingImp
import com.jop.marketjp.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val localCartShoppingImp: LocalCartShoppingImp,
    application: Application
): BaseViewModel(application) {

    fun observeCountCart(): Flow<Int> {
        return localCartShoppingImp.getCountCartShopping()
    }
}