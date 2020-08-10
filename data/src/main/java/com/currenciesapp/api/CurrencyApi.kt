package com.currenciesapp.api

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {

    @GET("?base={currency}")
    suspend fun getRatesFor(
        @Path("currency") currencyName: String
    ): Map<String, Float>
}