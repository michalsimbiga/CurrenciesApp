package com.currenciesapp.ui.rates

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.currenciesapp.DEFAULT_CURRENCY
import com.currenciesapp.DEFAULT_VOLUME
import com.currenciesapp.model.CurrencyItem
import com.currenciesapp.model.RatesItem

data class RatesViewState(
    val currencyList: Async<RatesItem> = Uninitialized,
    val currentCurrency: Async<String> = Success(DEFAULT_CURRENCY),
    val currentRate: Async<Double> = Success(DEFAULT_VOLUME)
) : MvRxState
