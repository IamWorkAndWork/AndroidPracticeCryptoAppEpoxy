package com.example.domain.model.response.coin


import com.google.gson.annotations.SerializedName

data class DataDetails(
    @SerializedName("coin")
    val coinDetails: CoinDetails? = null
)