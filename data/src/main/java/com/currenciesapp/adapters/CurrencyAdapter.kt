package com.currenciesapp.adapters

import com.currenciesapp.model.dto.CurrencyDto
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

object CurrencyAdapter {

    @FromJson
    fun fromJson(map: Map<String, Double>): List<CurrencyDto> =
        map.flatMapTo(mutableListOf(),
            { (currency, rate) -> listOf(prepareCurrencyDto(currency, rate)) }
        )

    @ToJson
    fun toJson(list: MutableList<CurrencyDto>) =
        list.map { it.code to it.rate }.toMap()

    private fun prepareCurrencyDto(code: String, rate: Double) = CurrencyDto(code, rate)
}