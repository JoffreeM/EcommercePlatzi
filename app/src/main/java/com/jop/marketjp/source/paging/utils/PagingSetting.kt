package com.jop.marketjp.source.paging.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jop.marketjp.source.paging.BasePagingSource
import kotlinx.coroutines.flow.Flow

object PagingSetting {
    private const val MAX_ITEMS = 10
    private const val PREFETCH_ITEMS = 3

    fun <T : Any> createPager(
        apiCall: suspend (offset: Int, limit: Int) -> ResponseWrapper<T>
    ): Flow<PagingData<T>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = {
                BasePagingSource(apiCall)
            }
        ).flow
    }
}