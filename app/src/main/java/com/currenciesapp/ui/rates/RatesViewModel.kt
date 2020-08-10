package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.model.Currency
import com.currenciesapp.model.CurrencyItem
import com.currenciesapp.model.toItem
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class RatesViewModel(
    state: RatesViewState,
    private val getRatesUseCase: GetRatesUseCase
) : MvRxViewModel<RatesViewState>(state) {

    init {
        viewModelScope.launch {
            getRatesUseCase.execute(
                params = GetRatesUseCase.Params(currencyName = "EUR"),
                mapper = { it.map(Currency::toItem) },
                stateReducer = { copy(currencyList = it) }
            )
        }
    }

    companion object :
        KoinMvRxViewModelFactory<RatesViewModel, RatesViewState>(RatesViewModel::class)
}
