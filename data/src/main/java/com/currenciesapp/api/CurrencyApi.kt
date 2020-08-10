package com.currenciesapp.api

import com.currenciesapp.model.RatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/api/android/latest")
    suspend fun getRatesFor(
        @Query(value = "base") currencyName: String
    ): Response<RatesResponse>
}