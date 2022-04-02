package com.example.domain.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class CoinModel(
    override val uuid: String,
    override val iconUrl: String,
    override val name: String,
    override val symbol: String,
    override val coinColor: String,
    override val change: String,
    override val price: String,
    @ColorRes
    val chengeTextColor: Int,
    @DrawableRes
    val changeIcon: Int
) : BaseCoinModel(uuid, iconUrl, name, symbol, coinColor, change, price)