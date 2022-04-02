package com.features.crypto.presentation.home.epoxy

import androidx.core.content.ContextCompat
import coil.load
import com.example.core.Constants
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.CoinModel
import com.features.crypto.R
import com.features.crypto.databinding.EpoxyItemTop3Binding
import com.features.crypto.databinding.ItemTop3DetailsBinding
import com.features.crypto.utils.ViewBindingKotlinModel

data class Top3EpoxyItem(
    private val coinModel: CoinHomeUiModel.Top3RankingItem,
    private val onCryptoClicked: (CoinModel) -> Unit
) : ViewBindingKotlinModel<EpoxyItemTop3Binding>(R.layout.epoxy_item_top_3) {

    override fun EpoxyItemTop3Binding.bind() {

        coinModel.coinModelList.forEachIndexed { index, coinModel ->

            when (index) {
                0 -> itemRank1.bind(coinModel)
                1 -> itemRank2.bind(coinModel)
                2 -> itemRank3.bind(coinModel)
            }

        }

    }

    private fun ItemTop3DetailsBinding.bind(coinModel: CoinModel) {
        iconImageView.load(coinModel.iconUrl) {
            crossfade(Constants.CROSSFADE_DELAY)
            error(R.drawable.ic_baseline_photo)
        }

        symbolTextView.text = coinModel.symbol

        nameTextView.text = coinModel.name

        rateChangeTextView.text = coinModel.change
        rateChangeTextView.setTextColor(
            ContextCompat.getColor(
                rateChangeTextView.context,
                coinModel.chengeTextColor
            )
        )

        changeIconImageView.load(coinModel.changeIcon)

        parentCardView.setOnClickListener {
            onCryptoClicked.invoke(coinModel)
        }

    }

}


