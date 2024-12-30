package com.jop.marketjp.repository.category

import androidx.paging.PagingData
import com.jop.domain.models.category.CategoryResponse
import kotlinx.coroutines.flow.Flow

interface Category {
    suspend fun getCategoryAll(): Flow<PagingData<CategoryResponse>>
}