package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.Success
import com.currenciesapp.UPDATE_TIME_1_SEC
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.model.Rates
import com.currenciesapp.model.toItem
import com.currenciesapp.useCase.GetRatesFlowUseCase
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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

    private val mainContext: CoroutineContext
        get() = Dispatchers.Main + job

    private fun setNewRates(rates: Rates) {
        setState { copy(currencyList = Success(rates.toItem())) }
    }

    private fun setFlowCollector(flow: Flow<Rates>) =
        viewModelScope.launch(mainContext) {
            flow.collect { setNewRates(it) }
        }

    fun updateRates() = withState { state ->
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                getRatesUseCase.run(
                    params = GetRatesUseCase.Params(state.currentCurrency.invoke().toString())
                )
                delay(UPDATE_TIME_1_SEC)
            }
        }
    }

    private fun getRatesFlow() = withState { state ->
        viewModelScope.launch(coroutineContext) {
            val ratesFlow = getRatesFlowUseCase.run(
                GetRatesFlowUseCase.Params(state.currentCurrency.invoke() ?: return@launch)
            ).invoke() ?: return@launch

            setFlowCollector(ratesFlow)
        }
    }

    fun setNewBaseCurrency(newCurrencyCode: String) = setState {
        copy(currentCurrency = Success(newCurrencyCode))
    }.also {
        recreateCoroutineContext()
        getRatesFlow()
    }

    private fun recreateCoroutineContext() {
        job.cancel()
        job = Job()
    }

    fun setNewRate(newRate: Double) = setState { copy(currentRate = Success(newRate)) }

    companion object :
        KoinMvRxViewModelFactory<RatesViewModel, RatesViewState>(RatesViewModel::class)
}
