package com.currenciesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currenciesapp.model.entity.CurrencyEntity
import com.currenciesapp.model.entity.RatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(ratesEntity: RatesEntity)

    @Query("SELECT * FROM ratesentity WHERE baseCurrency LIKE :currencyCode")
    fun getRatesByCurrency(currencyCode: String): Flow<RatesEntity>
}