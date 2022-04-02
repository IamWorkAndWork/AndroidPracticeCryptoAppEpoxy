package com.example.domain.model.response.coins


import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("btcPrice")
    val btcPrice: Double? = null,
    @SerializedName("change")
    val change: Double? = null,
    @SerializedName("coinrankingUrl")
    val coinrankingUrl: String? = null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("24hVolume")
    val hVolume: String? = null,
    @SerializedName("iconUrl")
    val iconUrl: String? = null,
    @SerializedName("listedAt")
    val listedAt: Long? = null,
    @SerializedName("lowVolume")
    val lowVolume: Boolean? = null,
    @SerializedName("marketCap")
    val marketCap: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("rank")
    val rank: Int? = null,
    @SerializedName("sparkline")
    val sparkline: List<String>? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("tier")
    val tier: Int? = null,
    @SerializedName("uuid")
    val uuid: String? = null
)