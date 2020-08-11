package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import com.airbnb.mvrx.Success
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.model.Currency
import com.currenciesapp.model.CurrencyItem
import com.currenciesapp.model.toItem
import com.currenciesapp.ui.rates.view.CurrenccyUpdateWorker
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RatesViewModel(
    state: RatesViewState,
    private val getRatesUseCase: GetRatesUseCase
) : MvRxViewModel<RatesViewState>(state) {

    init {
        setNewRate(1f)
        updateRates()
    }

    fun updateRates() = withState { state ->
        viewModelScope.launch {
            getRatesUseCase.execute(
                params = GetRatesUseCase.Params(
                    currencyName = state.currentCurrency.invoke() ?: "EUR"
                ),
                mapper = { it.map(Currency::toItem) },
                stateReducer = {
                    copy(
                        currencyList = it,
                        currentCurrency = Success(it.invoke()?.first()?.code ?: "EUR")
                    )
                }
            )
        }
    }

    fun setNewBaseCurrency(newCurrencyCode: String) = setState {
        copy(currentCurrency = Success(newCurrencyCode))
    }.also { Timber.i("TESTING newCurrency $newCurrencyCode") }

    fun setNewRate(newRate: Float) = setState {
        copy(currentRate = Success(newRate))
    }.also { Timber.i("TESTING setNewRate $newRate") }

    companion object :
        KoinMvRxViewModelFactory<RatesViewModel, RatesViewState>(RatesViewModel::class)
}
