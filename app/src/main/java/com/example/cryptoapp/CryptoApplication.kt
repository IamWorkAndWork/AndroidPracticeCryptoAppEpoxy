package com.example.cryptoapp

import android.app.Application
import androidx.multidex.MultiDex
import com.example.data.di.dataModules
import com.example.domain.di.domainModules
import com.features.crypto.di.cryptoFeatureModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

@ExperimentalCoroutinesApi
class CryptoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CryptoApplication)
            modules(appModules)
        }

    }

    val appModules = mutableListOf<Module>().apply {
        addAll(dataModules)
        addAll(domainModules)
        addAll(cryptoFeatureModules)
    }

}