package com.jop.marketjp.repository.category

import androidx.paging.PagingData
import com.jop.domain.models.category.CategoryResponse
import com.jop.marketjp.source.paging.utils.PagingSetting
import com.jop.marketjp.source.paging.utils.ResponseWrapper
import com.jop.webs.ApiService
import com.jop.webs.BaseApiResponse
import com.jop.webs.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryImp @Inject constructor(
    private val apiService: ApiService
): Category, BaseApiResponse() {
    override suspend fun getCategoryAll(): Flow<NetworkResult<List<CategoryResponse>>> = safeApiCall {
        apiService.getCategoryAll()
    }

}