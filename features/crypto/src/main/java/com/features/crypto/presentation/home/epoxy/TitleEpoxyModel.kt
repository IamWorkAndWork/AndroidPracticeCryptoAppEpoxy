package com.features.crypto.presentation.home.epoxy

import com.example.domain.model.CoinHomeUiModel
import com.features.crypto.R
import com.features.crypto.databinding.EpoxyItemTitleBinding
import com.features.crypto.utils.ViewBindingKotlinModel

data class TitleEpoxyModel(
    private val model: CoinHomeUiModel.TitleItem
) : ViewBindingKotlinModel<EpoxyItemTitleBinding>(R.layout.epoxy_item_title) {

    override fun EpoxyItemTitleBinding.bind() {
        titleTextView.text = model.title
    }

}