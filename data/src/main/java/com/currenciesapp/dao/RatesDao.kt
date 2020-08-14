package com.currenciesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.currenciesapp.model.entity.RatesEntity

@Dao
interface RatesDao{

//    @Insert
//    fun insertRates(ratesEntity: RatesEntity)
//
//    @Query("Select * From ratesentity WHERE baseCurrency LIKE :currencyCode")
//    fun getRatesByCurrency(currencyCode: String)
}