package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.Success
import com.currenciesapp.DEFAULT_CURRENCY
import com.currenciesapp.DEFAULT_VOLUME
import com.currenciesapp.common.Result
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.model.Currency
import com.currenciesapp.model.Rates
import com.currenciesapp.model.toItem
import com.currenciesapp.useCase.GetRatesFlowUseCase
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private fun setFlowCollector(flow: Flow<Rates>) =
        viewModelScope.launch(coroutineContext) {
            flow.collect {
                setState { copy(currencyList = Success(it.toItem())) }
            }
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
        updateRates()
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
