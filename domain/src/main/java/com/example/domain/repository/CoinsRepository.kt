package com.example.domain.repository

import androidx.paging.PagingData
import com.example.core.model.ResultState
import com.example.domain.model.CoinDetailsModel
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.request.CoinsRequestModel
import com.example.domain.model.response.coin.CoinResponseModel
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {
    suspend fun getCoinsPagingData(request: CoinsRequestModel): Flow<PagingData<CoinHomeUiModel>>

    suspend fun getCoinDetails(uuid: String): Flow<ResultState<CoinDetailsModel>>
}