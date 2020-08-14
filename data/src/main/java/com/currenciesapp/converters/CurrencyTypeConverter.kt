package com.currenciesapp.converters

import androidx.room.TypeConverter
import com.currenciesapp.model.entity.CurrencyEntity

class CurrencyTypeConverter {

    @TypeConverter
    fun fromList(list: List<CurrencyEntity>): String =
        list.joinToString(separator = ":", transform = { it.toString() })

    @TypeConverter
    fun toList(string: String): List<CurrencyEntity> =
        string.split(":").fold(mutableListOf<CurrencyEntity>(), { acc, str ->
            val split = str.split(",")
            acc.add(CurrencyEntity(split.first(), split[1], split[2].toDouble()))
            return acc
        })
}