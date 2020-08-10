package com.currenciesapp.model

import com.squareup.moshi.Json

data class RatesResponse<out T>(
    @Json(name = "baseCurrency") val data: T?,
    @Json(name = "rates") val rates: Map<String, Float>
)