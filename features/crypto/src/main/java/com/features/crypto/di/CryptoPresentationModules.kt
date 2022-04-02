package com.features.crypto.di

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
val cryptoFeatureModules = listOf(cryptoHomeModule, cryptoDetailsModule)