package com.features.crypto.di

import com.features.crypto.presentation.details.CryptoDetailsViewModel
import com.features.crypto.presentation.home.CryptoHomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val cryptoDetailsModule = module {

    viewModel {
        CryptoDetailsViewModel(
            getCoinDetailsUseCase = get()
        )
    }

}