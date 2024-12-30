package com.jop.marketjp.repository.products

import com.jop.domain.models.product.ProductResponse
import com.jop.webs.NetworkResult
import kotlinx.coroutines.flow.Flow

interface Product {
    suspend fun getOneProduct(productId: Int): Flow<NetworkResult<ProductResponse>>
}