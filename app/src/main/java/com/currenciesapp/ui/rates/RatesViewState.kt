package com.currenciesapp.ui.rates

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.currenciesapp.model.CurrencyItem

data class RatesViewState(val currencyList: Async<List<CurrencyItem>> = Uninitialized) : MvRxState