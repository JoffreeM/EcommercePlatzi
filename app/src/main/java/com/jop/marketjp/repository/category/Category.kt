package com.jop.marketjp.repository.category

import com.jop.domain.models.category.CategoryResponse
import com.jop.webs.NetworkResult
import kotlinx.coroutines.flow.Flow

interface Category {
    suspend fun getCategoryAll(): Flow<NetworkResult<List<CategoryResponse>>>
}