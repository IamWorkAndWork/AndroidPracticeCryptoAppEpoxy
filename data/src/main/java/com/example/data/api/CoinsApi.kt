package com.example.data.api

import com.example.domain.model.response.coin.CoinResponseModel
import com.example.domain.model.response.coins.CoinsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CoinsApi {

    @GET("v2/coins")
    suspend fun getCoins(@QueryMap request: Map<String, String>): CoinsResponseModel

    @GET("v2/coin/{uuid}")
    suspend fun getCoinDetails(@Path("uuid") uuid: String): CoinResponseModel

}