package com.currenciesapp.dataSource

import com.currenciesapp.api.CurrencyApi
import com.currenciesapp.common.extensions.bodyOrException

class RemoteDataSource(private val currencyApi: CurrencyApi) {

    suspend fun getRates(currencyName: String) =
        currencyApi.getRatesFor(currencyName).bodyOrException()
}