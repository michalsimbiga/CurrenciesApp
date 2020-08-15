package com.currenciesapp.model

import com.currenciesapp.DEFAULT_VOLUME

data class RatesItem(
    val baseCurrency: CurrencyItem,
    val rates: List<CurrencyItem>
)

fun Rates.toItem() =
    RatesItem(
        baseCurrency = Currency(baseCurrency, DEFAULT_VOLUME).toItem(),
        rates = rates.map(Currency::toItem)
    )