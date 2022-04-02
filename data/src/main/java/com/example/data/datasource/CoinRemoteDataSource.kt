package com.example.data.datasource

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.Constants.PAGE_LIMIT
import com.example.core.model.ResultState
import com.example.data.api.CoinsApi
import com.example.data.mapper.CoinDetailsMapper
import com.example.data.mapper.CoinMapper
import com.example.data.mapper.CoinTop3Mapper
import com.example.domain.model.CoinDetailsModel
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.request.CoinsRequestModel
import com.example.domain.model.response.coin.CoinResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CoinsRemoteDataSource {
    suspend fun getCoinsPagingData(request: CoinsRequestModel): Flow<PagingData<CoinHomeUiModel>>

    suspend fun getCoinDetails(uuid: String): Flow<ResultState<CoinDetailsModel>>
}

class CoinsRemoteDataSourceImpl(
    private val context: Context,
    private val coinsApi: CoinsApi,
    private val coinMapper: CoinMapper,
    private val coinTop3Mapper: CoinTop3Mapper,
    private val coinDetailsMapper: CoinDetailsMapper
) : CoinsRemoteDataSource {

    companion object {
        private const val PREFETCH_DISTANCE = 1
    }

    override suspend fun getCoinsPagingData(request: CoinsRequestModel): Flow<PagingData<CoinHomeUiModel>> {

        val pagingConfig = PagingConfig(
            initialLoadSize = PAGE_LIMIT,
            pageSize = PAGE_LIMIT,
            prefetchDistance = PREFETCH_DISTANCE //make to fetch new data at the last items
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                CoinsPagingDataSource(
                    context = context,
                    coinsApi = coinsApi,
                    coinsRequestModel = request,
                    coinMapper = coinMapper,
                    coinTop3Mapper = coinTop3Mapper
                )
            }
        ).flow
    }

    override suspend fun getCoinDetails(uuid: String) = flow {
        val response = try {
            val coinDetailResponse = coinsApi.getCoinDetails(uuid)
            val mapResponse = coinDetailsMapper.transform(coinDetailResponse.dataDetails?.coinDetails)
            ResultState.Success(mapResponse)
        } catch (error: Exception) {
            ResultState.Error(error)
        }
        emit(response)
    }

}