package com.example.domain.model.response.coin


import com.google.gson.annotations.SerializedName

data class CoinResponseModel(
    @SerializedName("data")
    val dataDetails: DataDetails? = null,
    @SerializedName("status")
    val status: String? = null
)