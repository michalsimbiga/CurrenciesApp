package com.currenciesapp.model

data class CurrencyItem(
    val code: String,
    val fullName: String,
    val rate: Double
)

fun Currency.toItem() =
    CurrencyItem(
        code = code,
        fullName = fullName,
        rate = rate
    )
