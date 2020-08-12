package com.currenciesapp.repository

import com.currenciesapp.common.Result
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
                listOfCurrencies.add(
                    prepareCurrencyEntity(
                        currencyCode = currency.key,
                        currencyRate = currency.value
                    )
                )
            }
            listOfCurrencies.add(FIST_INDEX, prepareCurrencyEntity(currencyName, DEFAULT_RATE))

            return@safeCall listOfCurrencies.map(CurrencyEntity::toDomain)
        }

    private fun prepareCurrencyEntity(currencyCode: String, currencyRate: Float): CurrencyEntity {
        val javaCurrency = java.util.Currency.getInstance(currencyCode)

        return CurrencyEntity(
            code = currencyCode,
            rate = currencyRate,
            fullName = javaCurrency.displayName
        )
    }

    companion object {
        private const val FIST_INDEX = 0
        private const val DEFAULT_RATE = 1f
    }
}