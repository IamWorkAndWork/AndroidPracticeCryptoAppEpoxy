package com.example.data.di

import com.example.data.providers.NetworkProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {

    single {
        NetworkProvider.provideChuckerInterceptor(context = androidContext())
    }

    single {
        NetworkProvider.provideHttpLoggingInterceptor()
    }

    single {
        NetworkProvider.provideGsonConverterFactory()
    }

    single {
        NetworkProvider.provideOkHttpClient(
            interceptor = get(),
            chuckerInterceptor = get()
        )
    }

    single {
        NetworkProvider.provideRetrofit(
            okHttpClient = get(),
            gsonConverterFactory = get()
        )
    }

    single {
        NetworkProvider.provideCoinsApi(
            retrofit = get()
        )
    }

}