package com.example.data.datasource

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.Constants
import com.example.core.R
import com.example.data.api.CoinsApi
import com.example.data.mapper.CoinMapper
import com.example.data.mapper.CoinTop3Mapper
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.request.CoinsRequestModel
import com.example.domain.model.request.toQuery
import com.example.domain.model.request.toQuerySymbol

class CoinsPagingDataSource(
    private val context: Context,
    private val coinsApi: CoinsApi,
    private val coinsRequestModel: CoinsRequestModel,
    private val coinMapper: CoinMapper,
    private val coinTop3Mapper: CoinTop3Mapper
) : PagingSource<Int, CoinHomeUiModel>() {

    companion object {
        private const val ID_TITLE_TOP3 = "TitleTop3Crypto"
        private const val ID_TITLE_List = "TitleItemList"
    }

    private var countTotalLoadMore = 0

    override fun getRefreshKey(state: PagingState<Int, CoinHomeUiModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinHomeUiModel> {
        return try {

            val offset = params.key ?: 0

            val response =
                coinsApi.getCoins(
                    request = coinsRequestModel.apply {
                        this.limit = params.loadSize.toString()
                        this.offset = offset.toString()
                    }.toQuery()
                )

            val coinModelList =
                coinMapper.transform(response.data?.coins, countTotalLoadMore).toMutableList()
            countTotalLoadMore++
            /*
             * if search query is empty then fetch for the top 3 ranking list
             */
            if (offset == 0 && coinsRequestModel.searchQuery.isEmpty()) {

                val top3Item = getTop3CryptoModel()
                val top3TitleItem = getTop3CryptoTitleModel()

                val titleListItem = getTitleListItem()

                coinModelList.apply {
                    /*
                     * add with last sequence will render in top position of list
                     */
                    add(0, titleListItem)
                    add(0, top3Item)
                    add(0, top3TitleItem)
                }

            }

            val nextKey = getNextKey(coinModelList, offset, params)

            val prevKey = if (offset == 0) null else offset - 1

            if (coinModelList.isNullOrEmpty()) {

                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )

            } else {

                LoadResult.Page(
                    data = coinModelList,
                    prevKey = prevKey,
                    nextKey = nextKey
                )

            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getTop3CryptoTitleModel(): CoinHomeUiModel {
        return CoinHomeUiModel.TitleItem(id = ID_TITLE_TOP3, title = getTop3Title())
    }

    private suspend fun getTop3CryptoModel(): CoinHomeUiModel {
        val request = CoinsRequestModel().apply {
            this.orderBy = Constants.ORDER_BY_MARKET_CAP
            this.limit = "${Constants.TOP_3_SIZE}"
        }
        val top3Response = coinsApi.getCoins(request = request.toQuery())
        return coinTop3Mapper.transform(coinList = top3Response.data?.coins)
    }

    private fun getTitleListItem(): CoinHomeUiModel {
        val titleList = SpannableStringBuilder().append(context.getString(R.string.home_title_list))
        return CoinHomeUiModel.TitleItem(
            id = ID_TITLE_List,
            title = titleList
        )
    }

    private fun getTop3Title(): SpannableStringBuilder {
        val redCodeColor = if (context.isDarkMode) {
            ContextCompat.getColor(context, R.color.orange)
        } else {
            ContextCompat.getColor(context, R.color.red)
        }
        val titleTop3 = SpannableStringBuilder()
            .append(context.getString(R.string.home_title_top) + " ")
            .color(redCodeColor) { append("${Constants.TOP_3_SIZE}") }
            .append(" " + context.getString(R.string.home_title_rank_crypto))
        return titleTop3
    }

    val Context.isDarkMode
        get() = this.getString(R.string.mode) != "Day"

    private fun getNextKey(
        coinHomeModelList: List<CoinHomeUiModel>,
        offset: Int,
        params: LoadParams<Int>
    ) = if (coinHomeModelList.isNullOrEmpty()) {
        null
    } else {
        offset + params.loadSize
    }

}