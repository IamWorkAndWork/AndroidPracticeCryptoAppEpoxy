package com.example.domain.usecase

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.domain.model.CoinHomeUiModel
import com.example.domain.model.request.CoinsRequestModel
import com.example.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetCoinDataUseCase {
    suspend fun execute(request: CoinsRequestModel): Flow<PagingData<CoinHomeUiModel>>
}

class GetCoinDataUseCaseImpl(
    private val coinsRepository: CoinsRepository
) : GetCoinDataUseCase {

    override suspend fun execute(request: CoinsRequestModel): Flow<PagingData<CoinHomeUiModel>> {
        return coinsRepository.getCoinsPagingData(request = request)
    }

}