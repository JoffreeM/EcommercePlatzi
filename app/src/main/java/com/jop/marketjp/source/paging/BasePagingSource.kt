package com.jop.marketjp.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jop.marketjp.source.paging.utils.PagingSetting
import com.jop.marketjp.source.paging.utils.ResponseWrapper

class BasePagingSource<T : Any>(
    private val apiCall: suspend (offset: Int, limit: Int) -> ResponseWrapper<T>,
): PagingSource<Int, T>(){
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 0
            val offset = page * PagingSetting.MAX_ITEMS
            val limit = PagingSetting.MAX_ITEMS

            val response = apiCall(offset, limit)

            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (response.items.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = response.items,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            println("Error BasePagingSource $exception")
            LoadResult.Error(exception)
        }
    }
}