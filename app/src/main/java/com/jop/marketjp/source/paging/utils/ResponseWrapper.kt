package com.jop.marketjp.source.paging.utils

data class ResponseWrapper<T>(
    val items: List<T>,       // Lista de elementos de la API
    val offset: Int,
    val limit: Int
)