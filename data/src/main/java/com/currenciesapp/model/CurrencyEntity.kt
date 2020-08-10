package com.currenciesapp.model

data class CurrencyEntity(
    val code: String,
    val fullName: String,
    val rate: Float
)

fun CurrencyEntity.toDomain() = Currency(
    code = code,
    fullName = fullName,
    rate = rate
)