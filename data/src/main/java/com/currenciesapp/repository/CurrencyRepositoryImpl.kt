package com.currenciesapp.repository

import com.currenciesapp.common.Result
import com.currenciesapp.common.safeCall
import com.currenciesapp.dataSource.LocalDataSource
import com.currenciesapp.dataSource.RemoteDataSource
import com.currenciesapp.model.Rates
import com.currenciesapp.model.dto.toEntity
import com.currenciesapp.model.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class CurrencyRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CurrencyRepository {

    override suspend fun fetchRatesFor(currencyName: String): Result<Unit> =
        safeCall {
            remoteDataSource.getRates(currencyName)
                .toEntity()
                .apply { localDataSource.insertRates(this) }
            Unit
        }

    override suspend fun getRatesFlowFor(currencyName: String): Result<Flow<Rates>> =
        safeCall { localDataSource.getRatesFlow(currencyName).mapLatest { it.toDomain() } }
}