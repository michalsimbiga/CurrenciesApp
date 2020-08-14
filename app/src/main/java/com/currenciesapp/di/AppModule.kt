package com.currenciesapp.di

import androidx.room.Room
import com.currenciesapp.ApplicationDatabase
import com.currenciesapp.DATABASE_NAME
import com.currenciesapp.dao.RatesDao
import com.currenciesapp.ui.rates.RatesViewModel
import com.currenciesapp.ui.rates.RatesViewState
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { (state: RatesViewState) ->
        RatesViewModel(
            state = state,
            getRatesUseCase = get()
        )
    }

    single<ApplicationDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ApplicationDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single<RatesDao> {
        get<ApplicationDatabase>().ratesDao()
    }

}