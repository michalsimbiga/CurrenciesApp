package com.currenciesapp.dataSource

import com.currenciesapp.api.CurrencyApi

class RemoteDataSource(private val currencyApi: CurrencyApi) {

    suspend fun getRates(currencyName: String) =
        currencyApi.getRatesFor(currencyName)

}