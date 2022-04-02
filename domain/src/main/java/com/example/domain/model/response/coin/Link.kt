package com.example.domain.model.response.coin


import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
)