package com.currenciesapp.di

import com.currenciesapp.BASE_URL
import com.currenciesapp.MAX_TIMEOUT
//import com.currenciesapp.RatesResponseConverterFactory
import com.currenciesapp.api.CurrencyApi
import com.currenciesapp.dataSource.RemoteDataSource
import com.currenciesapp.repository.CurrencyRepository
import com.currenciesapp.repository.CurrencyRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single {
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            connectTimeout(MAX_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(MAX_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(MAX_TIMEOUT, TimeUnit.MILLISECONDS)
        }.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    single {
        Retrofit.Builder()
//            .addConverterFactory(RatesResponseConverterFactory)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(BASE_URL)
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(CurrencyApi::class.java) }

    single { RemoteDataSource(currencyApi = get()) }

    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            remoteDataSource = get()
        )
    }


    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}