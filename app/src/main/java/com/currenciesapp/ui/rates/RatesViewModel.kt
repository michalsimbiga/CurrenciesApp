package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.Success
import com.currenciesapp.DEFAULT_CURRENCY
import com.currenciesapp.DEFAULT_VOLUME
import com.currenciesapp.common.Result
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.model.Currency
import com.currenciesapp.model.toItem
import com.currenciesapp.useCase.GetRatesFlowUseCase
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class RatesViewModel(
    state: RatesViewState,
    private val getRatesUseCase: GetRatesUseCase,
    private val getRatesFlowUseCase: GetRatesFlowUseCase
) : MvRxViewModel<RatesViewState>(state) {

    init {
        setNewRate(DEFAULT_VOLUME)
        setNewBaseCurrency(DEFAULT_CURRENCY)
        updateRates()
    }

    fun updateRates() = withState { state ->
        viewModelScope.launch(Dispatchers.Default) {
            getRatesUseCase.run(
                params = GetRatesUseCase.Params(
                    currencyName = state.currentCurrency.invoke().toString()
                )
            )
        }
    }

    fun getRatesFlow() = withState { state ->
        viewModelScope.launch(Dispatchers.Default) {
            getRatesFlowUseCase.run(
                GetRatesFlowUseCase.Params(
                    state.currentCurrency.invoke() ?: DEFAULT_CURRENCY
                )
            ).invoke()?.collect {
                Timber.i("TESTING rates $it")
                setState { copy(currencyList = Success(it.rates.map(Currency::toItem))) }
            }
        }
    }

    fun setNewBaseCurrency(newCurrencyCode: String) = setState {
        copy(currentCurrency = Success(newCurrencyCode))
    }.also {
        Timber.i("TESTING setNewBaseCurrency $newCurrencyCode")
        getRatesFlow()
    }

    fun setNewRate(newRate: Float) = setState { copy(currentRate = Success(newRate)) }

    companion object :
        KoinMvRxViewModelFactory<RatesViewModel, RatesViewState>(RatesViewModel::class)
}
