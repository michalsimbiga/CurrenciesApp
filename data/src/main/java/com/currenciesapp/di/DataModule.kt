package com.currenciesapp.di

import com.currenciesapp.BASE_URL
import com.currenciesapp.MAX_TIMEOUT
import com.currenciesapp.adapters.CurrencyAdapter
import com.currenciesapp.api.CurrencyApi
import com.currenciesapp.dao.RatesDao
import com.currenciesapp.dataSource.LocalDataSource
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
            level = HttpLoggingInterceptor.Level.NONE
        }).build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(BASE_URL)
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(CurrencyApi::class.java) }

    single { RemoteDataSource(currencyApi = get()) }

    single { LocalDataSource(ratesDao = get()) }

    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(CurrencyAdapter)
            .build()
    }
}