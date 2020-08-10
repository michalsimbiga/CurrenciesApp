package com.currenciesapp.repository

import com.currenciesapp.common.Result
import com.currenciesapp.model.Currency

interface CurrencyRepository {

    suspend fun getRates(currencyName: String): Result<List<Currency>>
}