package com.jop.domain.models

import com.google.gson.annotations.SerializedName

data class BaseResponse <T>(
    val success: Boolean,
    val message: String,
    @SerializedName("data")
    val data: T
)
