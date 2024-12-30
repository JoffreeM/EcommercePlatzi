package com.jop.marketjp.repository.category

import androidx.paging.PagingData
import com.jop.domain.models.category.CategoryResponse
import com.jop.marketjp.source.paging.utils.PagingSetting
import com.jop.marketjp.source.paging.utils.ResponseWrapper
import com.jop.webs.ApiService
import com.jop.webs.BaseApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryImp @Inject constructor(
    private val apiService: ApiService
): Category, BaseApiResponse() {
    override suspend fun getCategoryAll(): Flow<PagingData<CategoryResponse>> {
        return PagingSetting.createPager { offset, limit ->
            val body = apiService.getCategoryAll(offset, limit)
            val response = safeDataPaging(body)
            ResponseWrapper(
                items = response ?: emptyList(),
                offset = offset,
                limit = limit
            )
        }
    }

}