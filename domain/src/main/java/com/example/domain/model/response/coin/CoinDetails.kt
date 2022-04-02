package com.example.domain.model.response.coin


import com.google.gson.annotations.SerializedName

data class CoinDetails(
    @SerializedName("allTimeHigh")
    val allTimeHigh: AllTimeHigh? = null,
    @SerializedName("btcPrice")
    val btcPrice: String? = null,
    @SerializedName("change")
    val change: Double? = null,
    @SerializedName("coinrankingUrl")
    val coinrankingUrl: String? = null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("24hVolume")
    val hVolume: String? = null,
    @SerializedName("iconUrl")
    val iconUrl: String? = null,
    @SerializedName("links")
    val links: List<Link>? = null,
    @SerializedName("listedAt")
    val listedAt: Int? = null,
    @SerializedName("marketCap")
    val marketCap: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("numberOfExchanges")
    val numberOfExchanges: Int? = null,
    @SerializedName("numberOfMarkets")
    val numberOfMarkets: Int? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("priceAt")
    val priceAt: Int? = null,
    @SerializedName("rank")
    val rank: Int? = null,
    @SerializedName("sparkline")
    val sparkline: List<String>? = null,
    @SerializedName("supply")
    val supply: Supply? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("websiteUrl")
    val websiteUrl: String? = null
)