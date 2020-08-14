package com.currenciesapp.repository

import com.currenciesapp.common.Result
import com.currenciesapp.model.Currency
import com.currenciesapp.model.Rates
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun fetchRatesFor(currencyName: String): Result<Unit>

    suspend fun getRatesFlowFor(currencyName: String): Result<Flow<Rates>>
}