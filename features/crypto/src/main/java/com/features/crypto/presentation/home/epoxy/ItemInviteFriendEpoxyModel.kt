package com.features.crypto.presentation.home.epoxy

import com.example.domain.model.CoinHomeUiModel
import com.features.crypto.R
import com.features.crypto.databinding.EpoxyItemInviteFriendsBinding
import com.features.crypto.utils.ViewBindingKotlinModel

class ItemInviteFriendEpoxyModel(
    private val uiModel: CoinHomeUiModel.InviteFriendItem,
    private val onClickedShare: (String) -> Unit
) : ViewBindingKotlinModel<EpoxyItemInviteFriendsBinding>(R.layout.epoxy_item_invite_friends) {

    override fun EpoxyItemInviteFriendsBinding.bind() {
        inviteFriendsText.text = uiModel.inviteFriendsText

        parentCardView.setOnClickListener {

            onClickedShare.invoke(uiModel.shareUrl)

        }
    }

}