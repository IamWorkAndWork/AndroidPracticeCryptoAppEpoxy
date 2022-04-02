package com.example.domain.di

import com.example.domain.usecase.GetCoinDataUseCase
import com.example.domain.usecase.GetCoinDataUseCaseImpl
import com.example.domain.usecase.GetCoinDetailsUseCase
import com.example.domain.usecase.GetCoinDetailsUseCaseImpl
import org.koin.dsl.module

val usecaseModule = module {

    factory<GetCoinDataUseCase> {
        GetCoinDataUseCaseImpl(
            coinsRepository = get()
        )
    }

    factory<GetCoinDetailsUseCase> {
        GetCoinDetailsUseCaseImpl(
            coinsRepository = get()
        )
    }

}