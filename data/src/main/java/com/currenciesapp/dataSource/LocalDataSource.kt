package com.currenciesapp.dataSource

import com.currenciesapp.dao.RatesDao

class LocalDataSource(private val ratesDao: RatesDao) {

//    suspend fun getRatesFlow(currencyName: String) =
//        ratesDao.getRatesByCurrency(currencyName)
}