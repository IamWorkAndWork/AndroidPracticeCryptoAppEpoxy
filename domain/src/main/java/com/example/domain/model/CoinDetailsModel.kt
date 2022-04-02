package com.example.domain.model

import android.text.SpannableStringBuilder
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class CoinDetailsModel(
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
    val changeIcon: Int,
    val description: String,
    val marketCap: String,
    val coinrankingUrl: String,
    val nameAndSymbol: SpannableStringBuilder
) : BaseCoinModel(uuid, iconUrl, name, symbol, coinColor, change, price)