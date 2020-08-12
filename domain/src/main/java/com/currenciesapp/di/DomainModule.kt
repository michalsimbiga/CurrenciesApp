package com.currenciesapp.di

import com.currenciesapp.useCase.GetRatesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetRatesUseCase(currencyRepository = get()) }
}
