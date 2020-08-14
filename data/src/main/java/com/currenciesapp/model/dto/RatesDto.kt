package com.currenciesapp.model.dto

import com.currenciesapp.model.entity.RatesEntity
import com.squareup.moshi.Json

data class RatesDto(
    @Json(name = "baseCurrency") val baseCurrency: String,
    @Json(name = "rates") val rates: MutableList<CurrencyDto>
)

fun RatesDto.toEntity() =
    RatesEntity(
        baseCurrency = baseCurrency,
        rates = rates.map(CurrencyDto::toEntity)
    )