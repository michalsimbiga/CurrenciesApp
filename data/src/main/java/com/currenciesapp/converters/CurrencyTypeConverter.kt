package com.currenciesapp.converters

import androidx.room.TypeConverter
import com.currenciesapp.model.entity.CurrencyEntity
import com.currenciesapp.model.entity.RatesEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import java.util.Currency

class CurrencyTypeConverter {

    @TypeConverter
    fun fromList(list: List<CurrencyEntity>): String = list.joinToString(
        separator = ":",
        transform = { currency -> "${currency.code},${currency.rate}" })

    @TypeConverter
    fun toList(string: String): List<CurrencyEntity> {
        val listOfObject = mutableListOf<CurrencyEntity>()

        string.split(":").forEach { str ->
            val obj = str.split(",")
            listOfObject.add(CurrencyEntity(obj.first(), obj[1].toDouble()))
        }

        return listOfObject.toList()

    }
}