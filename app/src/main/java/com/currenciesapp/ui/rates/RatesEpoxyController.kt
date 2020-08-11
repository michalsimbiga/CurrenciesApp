package com.currenciesapp.ui.rates

import com.airbnb.epoxy.Typed3EpoxyController
import com.currenciesapp.model.CurrencyItem
import com.currenciesapp.ui.rates.view.currencyItemView

class RatesEpoxyController(
    private var onCurrencySelectedCallback: ((String) -> Unit)?,
    private var onRateChangedCallback: ((Float) -> Unit)?
) : Typed3EpoxyController<List<CurrencyItem>, String, Float>() {

    private var focusedCurrency: String? = null

    override fun buildModels(
        currencyList: List<CurrencyItem>?,
        currentCurrency: String,
        rate: Float
    ) {
        currencyList?.forEach { currency ->
            currencyItemView {
                id(currency.code)
                defaultCurrency(currentCurrency == currency.code)
                rate(rate)
                currencyModel(currency)
                onCurrencyNameChanged(::onCurrentlySelected)
                onCurrencyRateChanged(onRateChangedCallback)
            }
        }
    }

    private fun onCurrentlySelected(name: String?) {
        focusedCurrency = name
        onCurrencySelectedCallback?.invoke(name ?: return)
    }

    fun clearCallbacks() {
        onCurrencySelectedCallback = null
        onRateChangedCallback = null
    }
}