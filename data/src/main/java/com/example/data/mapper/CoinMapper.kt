package com.example.data.mapper

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.text.color
import com.example.core.Constants.PAGE_LIMIT
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
import java.util.*

class CoinMapper(
    private val context: Context
) {

    companion object {
        var inviteFriendsPosition = 5
        const val MULTIPLY_BY = 2
    }

    fun transform(coinList: List<Coin>?, countTotalLoadMore: Int): List<CoinHomeUiModel> {

        val startPosition = PAGE_LIMIT * countTotalLoadMore

        if (countTotalLoadMore == 0) {
            inviteFriendsPosition = 5
        }

        val uiList = mutableListOf<CoinHomeUiModel>()

        coinList?.mapIndexed { index, coin ->

            val positionOfAllList = startPosition + index + 1

//            println("itemaaa ${coin.name} | $index | $allItemPosition | inviteFriendsPosition = $inviteFriendsPosition")

            if (isInviteFriendPosition(positionOfAllList)) {
                uiList.add(getInvitesFriendItem())
            }

            val id = coin.uuid.toString()

            val coinModel = CoinModel(
                uuid = coin.uuid.orEmpty(),
                iconUrl = coin.iconUrl.orEmpty().replace(SVG, PNG),
                name = coin.name.orEmpty(),
                symbol = coin.symbol.orEmpty(),
                coinColor = coin.color.orEmpty(),
                chengeTextColor = coin.change.getCoinColor(),
                change = coin.change.to2DecimalFormat(),
                price = coin.price.to5DecimalFormat(context.getString(R.string.common_prefix)),
                changeIcon = coin.change.getChangeIcon()
            )

            uiList.add(CoinHomeUiModel.CryptoItem(id, coinModel))

        }

        return uiList.toList()

    }

    private fun isInviteFriendPosition(allItemPosition: Int): Boolean {
        return if (allItemPosition == inviteFriendsPosition) {
            inviteFriendsPosition *= MULTIPLY_BY
            true
        } else {
            false
        }
    }

    private fun getInvitesFriendItem(): CoinHomeUiModel.InviteFriendItem {
        val textBlueColor = ContextCompat.getColor(context, R.color.text_blue)

        val inviteFirendsText = SpannableStringBuilder()
            .append(context.getString(R.string.home_text_invite_frienss_details) + " ")
            .color(textBlueColor) {
                append(context.getString(R.string.home_text_invite_friends))
            }
        val shareUrl = context.getString(R.string.share_invite_friends_url)

        return CoinHomeUiModel.InviteFriendItem(Date().time.toString(), inviteFirendsText, shareUrl)
    }

}