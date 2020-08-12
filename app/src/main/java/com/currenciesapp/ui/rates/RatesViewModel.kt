package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.Success
import com.currenciesapp.DEFAULT_CURRENCY
import com.currenciesapp.DEFAULT_VOLUME
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.model.Currency
import com.currenciesapp.model.toItem
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.launch

class RatesViewModel(
    state: RatesViewState,
    private val getRatesUseCase: GetRatesUseCase
) : MvRxViewModel<RatesViewState>(state) {

    init {
        setNewRate(DEFAULT_VOLUME)
        setNewBaseCurrency(DEFAULT_CURRENCY)
        updateRates()
    }

    fun updateRates() = withState { state ->
        viewModelScope.launch {
            getRatesUseCase.execute(
                params = GetRatesUseCase.Params(
                    currencyName = state.currentCurrency.invoke().toString()
                ),
                mapper = { it.map(Currency::toItem) },
                stateReducer = { copy(currencyList = it) }
            )
        }
    }

    fun setNewBaseCurrency(newCurrencyCode: String) = setState {
        copy(currentCurrency = Success(newCurrencyCode))
    }

    fun setNewRate(newRate: Float) = setState { copy(currentRate = Success(newRate)) }

    companion object :
        KoinMvRxViewModelFactory<RatesViewModel, RatesViewState>(RatesViewModel::class)
}
