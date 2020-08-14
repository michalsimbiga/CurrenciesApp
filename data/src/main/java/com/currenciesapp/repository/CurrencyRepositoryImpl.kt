package com.currenciesapp.repository

import com.currenciesapp.common.Result
import com.currenciesapp.common.safeCall
import com.currenciesapp.dataSource.LocalDataSource
import com.currenciesapp.dataSource.RemoteDataSource
import com.currenciesapp.model.Currency
import com.currenciesapp.model.dto.CurrencyDto
import com.currenciesapp.model.dto.RatesDto
import com.currenciesapp.model.dto.toEntity
import com.currenciesapp.model.entity.CurrencyEntity
import com.currenciesapp.model.entity.RatesEntity
import com.currenciesapp.model.entity.toDomain

class CurrencyRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CurrencyRepository {

    override suspend fun getRates(currencyName: String): Result<List<Currency>> =
        safeCall {
            remoteDataSource.getRates(currencyName)
                .insertCurrency(CurrencyDto(currencyName, DEFAULT_RATE))
                .toEntity()
                .apply { localDataSource.insertRates(this) }
                .rates
                .map(CurrencyEntity::toDomain)
        }

    companion object {
        private const val DEFAULT_RATE = 1.0
    }
}