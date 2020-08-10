package com.currenciesapp.model

data class CurrencyEntity(
    val name: String,
    val rate: Float
)

fun CurrencyEntity.toDomain() = Currency(
    name = name,
    rate = rate
)