package com.currenciesapp.converters

import androidx.room.TypeConverter
import com.currenciesapp.common.extensions.colon
import com.currenciesapp.common.extensions.comma
import com.currenciesapp.common.extensions.semicolon
import com.currenciesapp.model.entity.CurrencyEntity

class CurrencyTypeConverter {

    // TODO SIMPLIFY THE CONVERTER

    @TypeConverter
    fun fromList(list: List<CurrencyEntity>): String = list.joinToString(
        separator = String.colon,
        transform = { currency -> "${currency.code},${currency.rate}" })

    @TypeConverter
    fun toList(string: String): List<CurrencyEntity> {
        val listOfObject = mutableListOf<CurrencyEntity>()

        string.split(String.colon).forEach { str ->
            val obj = str.split(String.comma)
            listOfObject.add(CurrencyEntity(obj.first(), obj[1].toDouble()))
        }

        return listOfObject.toList()

    }
}