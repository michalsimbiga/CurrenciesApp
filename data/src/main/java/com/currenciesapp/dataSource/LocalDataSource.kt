package com.currenciesapp.dataSource

import com.currenciesapp.dao.RatesDao
import com.currenciesapp.model.entity.RatesEntity

class LocalDataSource(private val ratesDao: RatesDao) {

    suspend fun insertRates(ratesEntity: RatesEntity) = ratesDao.insertRates(ratesEntity)

//    suspend fun getRatesFlow(currencyName: String) =
//        ratesDao.getRatesByCurrency(currencyName)
}