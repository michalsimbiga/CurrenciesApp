package com.currenciesapp.model.entity

import com.currenciesapp.model.Currency

data class CurrencyEntity(
        val code: String,
        val fullName: String,
        val rate: Double
    )

fun CurrencyEntity.toDomain() =
    Currency(
        code = code,
        fullName = fullName,
        rate = rate
    )