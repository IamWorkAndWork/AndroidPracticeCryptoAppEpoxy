package com.features.crypto.presentation.home

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.CoinModel
import com.features.crypto.presentation.home.epoxy.*
import java.util.*

class CryptoUiController(
    private val onCryptoClicked: (CoinModel) -> Unit,
    private val onClickedShare: (String) -> Unit
) : PagingDataEpoxyController<CoinHomeUiModel>() {

    var isLoading: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    var isLoadMore: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: CoinHomeUiModel?): EpoxyModel<*> {

        return item?.let { uiModel ->

            when (uiModel) {

                is CoinHomeUiModel.TitleItem -> {

                    TitleEpoxyModel(uiModel).id(uiModel.id)

                }
                is CoinHomeUiModel.Top3RankingItem -> {

                    Top3EpoxyItem(uiModel, onCryptoClicked).id(uiModel.id)

                }
                is CoinHomeUiModel.CryptoItem -> {

                    ItemCryptoEpoxyModel(uiModel, onCryptoClicked).id(uiModel.id)

                }
                is CoinHomeUiModel.InviteFriendItem -> {

                    ItemInviteFriendEpoxyModel(uiModel, onClickedShare).id(uiModel.id)

                }

            }


        } ?: kotlin.run {

            return ItemLoadingModel().id(Date().time)

        }

    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        if (isLoading) {

            return ItemLoadingModel().id(Date().time)
                .addTo(this)

        } else {

            if (models.isEmpty()){
                return ItemEmptyEpoxyModel().id(Date().time)
                    .addTo(this)
            }
            else{

                super.addModels(models)

                ItemLoadingModel().id(Date().time)
                    .addIf(isLoadMore, this)
            }

        }

    }

}