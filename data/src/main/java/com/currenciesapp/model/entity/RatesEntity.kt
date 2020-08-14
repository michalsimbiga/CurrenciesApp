package com.currenciesapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currenciesapp.model.Rates

@Entity
data class RatesEntity(
    @PrimaryKey val baseCurrency: String,
    val rates: List<CurrencyEntity>
)

fun RatesEntity.toDomain() =
    Rates(
        baseCurrency = baseCurrency,
        rates = rates.map(CurrencyEntity::toDomain)
    )