package com.currenciesapp.repository

import com.currenciesapp.common.Result

interface CurrencyRepository {

    suspend fun getRates(currencyName: String) : Result<Map<String,Float>>
}