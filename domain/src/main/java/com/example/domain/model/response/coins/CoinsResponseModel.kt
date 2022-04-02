package com.example.domain.model.response.coins


import com.google.gson.annotations.SerializedName

data class CoinsResponseModel(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("status")
    val status: String? = null
)