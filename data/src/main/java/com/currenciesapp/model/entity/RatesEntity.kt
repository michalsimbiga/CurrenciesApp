package com.currenciesapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RatesEntity(
    @PrimaryKey val baseCurrency: String,
    val rates: List<CurrencyEntity>
)