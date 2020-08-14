package com.currenciesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currenciesapp.model.entity.CurrencyEntity
import com.currenciesapp.model.entity.RatesEntity

@Dao
interface RatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(ratesEntity: RatesEntity)

    @Query("SELECT * FROM ratesentity WHERE baseCurrency IS :currencyCode")
    fun getRatesByCurrency(currencyCode: String): RatesEntity
}