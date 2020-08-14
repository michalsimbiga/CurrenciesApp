package com.currenciesapp.model.dto

import com.currenciesapp.model.entity.CurrencyEntity

data class CurrencyDto(
    val code: String,
    val rate: Double
)

fun CurrencyDto.toEntity() =
    CurrencyEntity(
        code = code,
        fullName = java.util.Currency.getInstance(code).displayName,
        rate = rate
    )