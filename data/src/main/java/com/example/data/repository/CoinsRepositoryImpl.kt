package com.example.data.repository

import androidx.paging.PagingData
import com.example.core.model.ResultState
import com.example.data.datasource.CoinsRemoteDataSource
import com.example.domain.model.CoinDetailsModel
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.request.CoinsRequestModel
import com.example.domain.model.response.coin.CoinResponseModel
import com.example.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow

class CoinsRepositoryImpl(
    private val coinsRemoteDataSource: CoinsRemoteDataSource
) : CoinsRepository {

    override suspend fun getCoinsPagingData(request: CoinsRequestModel): Flow<PagingData<CoinHomeUiModel>> {
        return coinsRemoteDataSource.getCoinsPagingData(request = request)
    }

    override suspend fun getCoinDetails(uuid: String): Flow<ResultState<CoinDetailsModel>> {
        return coinsRemoteDataSource.getCoinDetails(uuid = uuid)
    }

}