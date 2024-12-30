package com.jop.webs

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

abstract class BaseApiResponse {
    protected fun <R> safeApiCall(apiCall: suspend () -> Response<R>): Flow<NetworkResult<R>> {
        return flow {
            emit(NetworkResult.Loading)

            try {
                val response = apiCall()
                val code = response.code()

                if (response.isSuccessful) {
                    val body = response.body()
                    emit(NetworkResult.Success(body))
                } else {
                    Log.e("error Response", response.message())
                    Log.e("code error", response.code().toString())
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                Log.e("errorResponse",e.message ?: e.toString())
                emit(NetworkResult.Error(e.message ?: e.cause?.message ?: ""))
            }
        }
    }

    protected fun <T> safeDataPaging(body: Response<T>): T? {
        return body.body()
    }
}