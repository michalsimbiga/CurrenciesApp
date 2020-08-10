package com.currenciesapp.di

import com.currenciesapp.ui.rates.RatesViewModel
import com.currenciesapp.ui.rates.RatesViewState
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { (state: RatesViewState) ->
        RatesViewModel(
            state = state,
            getRatesUseCase = get()
        )
    }
}