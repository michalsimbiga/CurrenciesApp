package com.currenciesapp.repository

import com.currenciesapp.common.Result
import com.currenciesapp.common.extensions.bodyOrException
import com.currenciesapp.common.safeCall
import com.currenciesapp.dataSource.RemoteDataSource
import com.currenciesapp.model.Currency
import com.currenciesapp.model.CurrencyEntity
import com.currenciesapp.model.toDomain

class CurrencyRepositoryImpl(private val remoteDataSource: RemoteDataSource) : CurrencyRepository {

    override suspend fun getRates(currencyName: String): Result<List<Currency>> =
        safeCall {
            val listOfCurrencies: MutableList<CurrencyEntity> = mutableListOf()
            remoteDataSource.getRates(currencyName).rates.entries.forEach { currency ->
                listOfCurrencies.add(CurrencyEntity(currency.key, currency.value))
            }
            return@safeCall listOfCurrencies.map(CurrencyEntity::toDomain)
        }

}