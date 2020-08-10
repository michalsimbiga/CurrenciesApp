package com.currenciesapp.model

data class CurrencyItem(
    val name: String,
    val rate: Float
)

fun Currency.toItem() =
    CurrencyItem(
        name = name,
        rate = rate
    )