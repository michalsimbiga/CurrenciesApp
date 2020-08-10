package com.currenciesapp.ui.rates

import androidx.lifecycle.viewModelScope
import com.currenciesapp.common.ui.KoinMvRxViewModelFactory
import com.currenciesapp.common.ui.MvRxViewModel
import com.currenciesapp.useCase.GetRatesUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class RatesViewModel(
    state: RatesViewState,
    private val getRatesUseCase: GetRatesUseCase
) : MvRxViewModel<RatesViewState>(state) {

    init {
        viewModelScope.launch {
            getRatesUseCase.invoke(
                params = GetRatesUseCase.Params(currencyName = "EUR"),
                onFailure = { Timber.i("TESTING currencies failure $it") },
                onSuccess = { Timber.i("TESTING currencies $it") }
            )
        }
    }

    companion object :
        KoinMvRxViewModelFactory<RatesViewModel, RatesViewState>(RatesViewModel::class)
}
