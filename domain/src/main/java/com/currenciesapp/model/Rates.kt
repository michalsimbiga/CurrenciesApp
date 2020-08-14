package com.currenciesapp.model

data class Rates(
    val baseCurrency: String,
    val rates: List<Currency>
)