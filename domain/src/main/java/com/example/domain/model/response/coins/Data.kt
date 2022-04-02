package com.example.domain.model.response.coins


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("coins")
    val coins: List<Coin>? = null,
    @SerializedName("stats")
    val stats: Stats? = null
)