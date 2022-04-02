package com.features.crypto.presentation.home.epoxy

import androidx.core.content.ContextCompat
import coil.load
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.CoinModel
import com.features.crypto.R
import com.features.crypto.databinding.EpoxyItemCryptoBinding
import com.features.crypto.utils.ViewBindingKotlinModel

data class ItemCryptoEpoxyModel(
    private val uiModel: CoinHomeUiModel.CryptoItem,
    val onCryptoClicked: (CoinModel) -> Unit
) :
    ViewBindingKotlinModel<EpoxyItemCryptoBinding>(R.layout.epoxy_item_crypto) {

    override fun EpoxyItemCryptoBinding.bind() {

        val coinModel = uiModel.coinModel

        iconImageView.load(coinModel.iconUrl)

        nameTextView.text = coinModel.name
        symbolTextView.text = coinModel.symbol

        priceTextView.text = coinModel.price
        rateChangeTextView.text = coinModel.change
        rateChangeTextView.setTextColor(ContextCompat.getColor(rateChangeTextView.context,coinModel.chengeTextColor))

        changeIconImageView.load(coinModel.changeIcon)

        itemCardView.setOnClickListener {
            onCryptoClicked.invoke(coinModel)
        }

    }

}