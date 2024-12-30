package com.jop.webs

import com.jop.domain.models.category.CategoryResponse
import com.jop.domain.models.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

typealias AliasResponseApi <T> = Response<T>
interface ApiService {

    @GET(ApiConstance.wsPath + "products/{productId}")
    suspend fun getOneProduct(
        @Path("productId") id: Int
    ): AliasResponseApi<ProductResponse>

    @GET(ApiConstance.wsPath + "categories")
    suspend fun getCategoryAll(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): AliasResponseApi<List<CategoryResponse>>

    @GET(ApiConstance.wsPath + "categories/{categoryId}/products")
    suspend fun getProductsOfCategory(
        @Path("categoryId") id: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): AliasResponseApi<List<CategoryResponse>>
}