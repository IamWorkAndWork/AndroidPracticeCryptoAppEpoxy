package com.example.data.di

import com.example.data.datasource.CoinsRemoteDataSource
import com.example.data.datasource.CoinsRemoteDataSourceImpl
import com.example.data.mapper.CoinDetailsMapper
import com.example.data.mapper.CoinMapper
import com.example.data.mapper.CoinTop3Mapper
import com.example.data.repository.CoinsRepositoryImpl
import com.example.domain.repository.CoinsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    factory {
        CoinMapper(
            context = androidContext()
        )
    }

    factory {
        CoinTop3Mapper()
    }

    factory {
        CoinDetailsMapper(
            context = androidContext()
        )
    }

    single<CoinsRemoteDataSource> {
        CoinsRemoteDataSourceImpl(
            context = androidContext(),
            coinsApi = get(),
            coinMapper = get(),
            coinTop3Mapper = get(),
            coinDetailsMapper = get()
        )
    }

    single<CoinsRepository> {
        CoinsRepositoryImpl(
            coinsRemoteDataSource = get()
        )
    }

}