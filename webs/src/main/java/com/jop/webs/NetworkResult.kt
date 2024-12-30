package com.jop.webs

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T?): NetworkResult<T>()
    data class Error<out T>(val error: String): NetworkResult<T>()
    object Loading: NetworkResult<Nothing>()
}
