package com.currenciesapp.model

import com.squareup.moshi.Json

data class RatesResponse(
    @Json(name = "baseCurrency") val baseCurrency: String,
    @Json(name = "rates") val rates: Map<String, Float>
)