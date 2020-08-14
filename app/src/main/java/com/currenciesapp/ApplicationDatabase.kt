package com.currenciesapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.currenciesapp.converters.CurrencyTypeConverter
import com.currenciesapp.dao.RatesDao
import com.currenciesapp.model.entity.RatesEntity

@Database(entities = [RatesEntity::class], version = DATABASE_VERSION)
@TypeConverters(CurrencyTypeConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}