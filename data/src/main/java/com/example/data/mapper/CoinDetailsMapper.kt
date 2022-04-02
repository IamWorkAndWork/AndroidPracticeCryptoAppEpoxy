package com.example.data.mapper

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.text.color
import com.example.core.Constants
import com.example.core.R
import com.example.core.ext.to2DecimalFormat
import com.example.data.ext.getChangeIcon
import com.example.data.ext.getCoinColor
import com.example.data.ext.toPriceWording
import com.example.domain.model.CoinDetailsModel
import com.example.domain.model.response.coin.CoinDetails
import org.jsoup.Jsoup

class CoinDetailsMapper(
    private val context: Context
) {

    fun transform(coin: CoinDetails?): CoinDetailsModel {

        return CoinDetailsModel(
            uuid = coin?.uuid.orEmpty(),
            iconUrl = coin?.iconUrl.orEmpty().replace(Constants.SVG, Constants.PNG),
            name = coin?.name.orEmpty(),
            symbol = coin?.symbol.orEmpty(),
            coinColor = coin?.color.orEmpty(),
            chengeTextColor = coin?.change.getCoinColor(),
            change = coin?.change.to2DecimalFormat(prefix = context.getString(R.string.common_prefix) + " "),
            price = coin?.price.to2DecimalFormat(prefix = context.getString(R.string.common_prefix) + " "),
            changeIcon = coin?.change.getChangeIcon(),
            description = Jsoup.parse(coin?.description.orEmpty()).text(),
            marketCap = coin?.marketCap.toPriceWording(
                context,
                prefix = context.getString(R.string.common_prefix)
            ),
            coinrankingUrl = coin?.coinrankingUrl.orEmpty(),
            nameAndSymbol = getNameText(coin)
        )
    }

    private fun getNameText(coin: CoinDetails?): SpannableStringBuilder {
        val coinColor = ContextCompat.getColor(context, R.color.red)
        val color = if (coin?.color.isNullOrEmpty()) {
            defaultColor()
        } else {
            coin?.color?.toColorInt()
        }
        val name = SpannableStringBuilder()
            .color(color ?: defaultColor()) { append("${coin?.name}") }
            .append(" ${String.format(context.getString(R.string.text_symbol, coin?.symbol))}")
        return name
    }

    private fun defaultColor(): Int {
        return context.getString(R.string.color_black).toColorInt()
    }

}