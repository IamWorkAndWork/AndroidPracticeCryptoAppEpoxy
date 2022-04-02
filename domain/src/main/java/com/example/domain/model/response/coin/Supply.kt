package com.example.domain.model.response.coin


import com.google.gson.annotations.SerializedName

data class Supply(
    @SerializedName("circulating")
    val circulating: String? = null,
    @SerializedName("confirmed")
    val confirmed: Boolean? = null,
    @SerializedName("total")
    val total: String? = null
)