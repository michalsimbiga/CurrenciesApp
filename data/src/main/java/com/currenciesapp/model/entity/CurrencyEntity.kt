package com.currenciesapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currenciesapp.model.Currency

@Entity
data class CurrencyEntity(
    @PrimaryKey val code: String,
    val rate: Double
)

fun CurrencyEntity.toDomain() =
    Currency(
        code = code,
        rate = rate
    )