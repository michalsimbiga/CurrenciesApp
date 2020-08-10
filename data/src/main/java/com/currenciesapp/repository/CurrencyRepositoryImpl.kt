package com.currenciesapp.repository

import com.currenciesapp.common.Result
import com.currenciesapp.common.extensions.bodyOrException
import com.currenciesapp.common.safeCall
import com.currenciesapp.dataSource.RemoteDataSource

class CurrencyRepositoryImpl(private val remoteDataSource: RemoteDataSource) : CurrencyRepository {

    override suspend fun getRates(currencyName: String): Result<Map<String,Float>> =
        safeCall {
            remoteDataSource.getRates(currencyName).bodyOrException()
        }

}