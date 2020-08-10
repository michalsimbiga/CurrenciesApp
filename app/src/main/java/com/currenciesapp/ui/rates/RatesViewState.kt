package com.currenciesapp.ui.rates

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class RatesViewState(val rates : Async<Unit> = Uninitialized) : MvRxState