package com.currenciesapp.dataSource

import com.currenciesapp.dao.RatesDao
import com.currenciesapp.model.entity.RatesEntity
import kotlinx.coroutines.flow.filterNotNull

class LocalDataSource(private val ratesDao: RatesDao) {

    suspend fun insertRates(ratesEntity: RatesEntity) = ratesDao.insertRates(ratesEntity)

    fun getRatesFlow(currencyName: String) = ratesDao.getRatesByCurrency(currencyName).filterNotNull()
}