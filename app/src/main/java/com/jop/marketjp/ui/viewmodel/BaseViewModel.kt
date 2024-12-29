package com.jop.marketjp.ui.viewmodel


import android.app.Application

import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.jop.marketjp.App
import com.jop.marketjp.ui.utils.snackbar.IcoSnackbar
import com.jop.marketjp.ui.utils.snackbar.SnackbarManager
import com.jop.marketjp.ui.viewstate.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var _viewState: MutableStateFlow<ViewState>
    protected lateinit var viewState: StateFlow<ViewState>

    protected val viewModelIndependentScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    protected fun initViewState(state: ViewState) {
        _viewState = MutableStateFlow(state)
        viewState = _viewState.asStateFlow()
    }

    protected fun updateViewState(newViewState: ViewState) {
        _viewState.update { newViewState }
    }

    protected fun <T> currentViewState(): T = _viewState.value as T

    fun <T> getState() = viewState as StateFlow<T>

    protected fun showSnackbar(icoSnackbar: IcoSnackbar, message: String) {
        SnackbarManager.showSnackbar(icoSnackbar,message)
    }
    protected fun getString(@StringRes resId: Int): String {
        return App.appContext.getString(resId)
    }
    inline fun <reified T> Any?.castValue(): T? {
        return this as? T
    }
}