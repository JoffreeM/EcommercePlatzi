package com.jop.marketjp.repository.products

import com.jop.domain.models.product.ProductResponse
import com.jop.webs.ApiService
import com.jop.webs.BaseApiResponse
import com.jop.webs.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductImp @Inject constructor(
    private val apiService: ApiService
): Product, BaseApiResponse() {
    override suspend fun getOneProduct(productId: Int): Flow<NetworkResult<ProductResponse>> = safeApiCall {
        apiService.getOneProduct(productId)
    }

    override suspend fun getProductAll(nameProduct: String, priceMin: Int?, priceMax: Int?, categoryId: Int?): Flow<NetworkResult<List<ProductResponse>>> = safeApiCall {
        apiService.getProductsAll(nameProduct, priceMin, priceMax, categoryId)
    }

    override suspend fun getProductsOfCategory(categoryId: Int): Flow<NetworkResult<List<ProductResponse>>> = safeApiCall {
        apiService.getProductsOfCategory(categoryId)
    }

}