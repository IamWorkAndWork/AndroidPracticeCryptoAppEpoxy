package com.example.domain.model.response.coin


import com.google.gson.annotations.SerializedName

data class AllTimeHigh(
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("timestamp")
    val timestamp: Int? = null
)