package com.example.data.mapper

import com.example.core.Constants.PNG
import com.example.core.Constants.SVG
import com.example.core.R
import com.example.core.ext.to2DecimalFormat
import com.example.core.ext.to5DecimalFormat
import com.example.data.ext.getChangeIcon
import com.example.data.ext.getCoinColor
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.CoinModel
import com.example.domain.model.response.coins.Coin

class CoinTop3Mapper {

    companion object {
        private const val ID = "Top3Crypto"
    }

    fun transform(coinList: List<Coin>?): CoinHomeUiModel {

        val top3CoinModelList = getTop3CoinModelList(coinList)

        val top3Item = CoinHomeUiModel.Top3RankingItem(
            id = ID,
            coinModelList = top3CoinModelList
        )

        return top3Item

    }

    private fun getTop3CoinModelList(coinList: List<Coin>?): List<CoinModel> {
        return coinList?.mapIndexed { index, coin ->

            CoinModel(
                uuid = coin.uuid.orEmpty(),
                iconUrl = coin.iconUrl.orEmpty().replace(SVG, PNG),
                name = coin.name.orEmpty(),
                symbol = coin.symbol.orEmpty(),
                coinColor = coin.color.orEmpty(),
                chengeTextColor = coin.change.getCoinColor(),
                change = coin.change.to2DecimalFormat(),
                price = coin.price.to5DecimalFormat(),
                changeIcon = coin.change.getChangeIcon()
            )


        } ?: emptyList()
    }

}