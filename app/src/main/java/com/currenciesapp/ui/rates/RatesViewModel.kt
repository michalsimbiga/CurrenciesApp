package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.Success
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.model.Rates
import com.currenciesapp.model.toItem
import com.currenciesapp.useCase.GetRatesFlowUseCase
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RatesViewModel(
    state: RatesViewState,
    private val getRatesUseCase: GetRatesUseCase,
    private val getRatesFlowUseCase: GetRatesFlowUseCase
) : MvRxViewModel<RatesViewState>(state) {

    init {
        updateRates()
        getRatesFlow()
    }

    private var job: Job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val coroutineContextIo: CoroutineContext
        get() = Dispatchers.IO + job

    private fun setNewRates(rates: Rates) {
        setState { copy(currencyList = Success(rates.toItem())) }
    }

    private fun updateRates() = withState { state ->
        viewModelScope.launch(coroutineContextIo) {
            while (true) {
                getRatesUseCase.run(
                    params = GetRatesUseCase.Params(state.currentCurrency.invoke().toString())
                )
                delay(1500)
            }
        }
    }

    private fun getRatesFlow() = withState { state ->
        viewModelScope.launch(coroutineContext) {
            val ratesFlow = getRatesFlowUseCase.run(
                GetRatesFlowUseCase.Params(state.currentCurrency.invoke() ?: return@launch)
            ).invoke() ?: return@launch

            ratesFlow.collect {
                setNewRates(it)
            }
        }
    }

    fun setNewBaseCurrency(newCurrencyCode: String) = setState {
        copy(currentCurrency = Success(newCurrencyCode))
    }.also {
        recreateCoroutineContext()
        updateRates()
        getRatesFlow()
    }

    private fun recreateCoroutineContext() {
        job.cancel()
        job = Job()
    }

    fun setNewRate(newRate: Double) = setState { copy(currentRate = Success(newRate)) }

    override fun onCleared() {
        job.cancel()

        super.onCleared()
    }

    companion object :
        KoinMvRxViewModelFactory<RatesViewModel, RatesViewState>(RatesViewModel::class)
}
