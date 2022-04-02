package com.example.domain.usecase

import com.example.core.model.ResultState
import com.example.domain.model.CoinDetailsModel
import com.example.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow

interface GetCoinDetailsUseCase {
    suspend fun execute(uuid: String): Flow<ResultState<CoinDetailsModel>>
}

class GetCoinDetailsUseCaseImpl(
    private val coinsRepository: CoinsRepository
) : GetCoinDetailsUseCase {

    override suspend fun execute(uuid: String): Flow<ResultState<CoinDetailsModel>> {
        return coinsRepository.getCoinDetails(uuid = uuid)
    }

}