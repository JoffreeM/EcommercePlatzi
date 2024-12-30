package com.jop.marketjp.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jop.marketjp.source.paging.utils.ResponseWrapper

class BasePagingSource<T : Any>(
    private val apiCall: suspend (offset: Int, limit: Int) -> ResponseWrapper<T>,
): PagingSource<Int, T>(){
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize // Tamaño de la página
            val response = apiCall(offset, limit)

            val nextKey = if (response.items.isNotEmpty()) offset + limit else null
            val prevKey = if (offset > 0) offset - limit else null

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