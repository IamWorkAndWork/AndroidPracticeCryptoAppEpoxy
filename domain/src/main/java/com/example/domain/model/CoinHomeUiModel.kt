package com.example.domain.model

import android.text.SpannableStringBuilder

sealed class CoinHomeUiModel {

    abstract val id: String

    class CryptoItem(
        override val id: String,
        val coinModel: CoinModel
    ) : CoinHomeUiModel()

    class InviteFriendItem(
        override val id: String,
        val inviteFriendsText: SpannableStringBuilder,
        val shareUrl: String
    ) : CoinHomeUiModel()

    class Top3RankingItem(
        override val id: String,
        val coinModelList: List<CoinModel>
    ) : CoinHomeUiModel()

    class TitleItem(
        override val id: String,
        val title: SpannableStringBuilder
    ) : CoinHomeUiModel()

}