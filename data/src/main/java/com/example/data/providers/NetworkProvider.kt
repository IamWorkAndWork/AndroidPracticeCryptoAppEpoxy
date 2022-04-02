package com.example.data.providers

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.data.BuildConfig
import com.example.data.api.CoinsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {

    private const val KEY_TOKEN = "x-access-token"
    private const val KEY_ACCEPT = "Accept"
    private const val APPLICATION_TYPE = "application/json"

    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context = context)
            .collector(chuckerCollector)
            .build()
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(chuckerInterceptor)
            .setApiKey()
            .build()
    }

    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    fun provideCoinsApi(retrofit: Retrofit): CoinsApi {
        return retrofit.create(CoinsApi::class.java)
    }

    fun OkHttpClient.Builder.setApiKey(): OkHttpClient.Builder = addInterceptor { chain ->
        val original = chain.request()

        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(KEY_TOKEN, BuildConfig.TOKEN)
            .build()

        val requestBuilder = original.newBuilder()
            .header(KEY_ACCEPT, APPLICATION_TYPE)
            .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }
}


