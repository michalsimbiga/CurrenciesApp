package com.currenciesapp.model

data class RatesItem(
    val baseCurrency: String,
    val rates: List<CurrencyItem>
)

fun Rates.toItem() =
    RatesItem(
        baseCurrency = baseCurrency,
        rates = rates.map(Currency::toItem)
    )