package com.currenciesapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class RatesEntity(
     val baseCurrency: String,
    val rates: List<CurrencyEntity>
)