package com.currenciesapp.ui.rates

import com.airbnb.epoxy.Typed3EpoxyController
import com.currenciesapp.model.CurrencyItem
import com.currenciesapp.ui.rates.view.currencyItemView

class RatesEpoxyController(
    private var onCurrencyChangedCallback: ((String) -> Unit)?,
    private var onVolumeChangedCallback: ((Float) -> Unit)?
) : Typed3EpoxyController<List<CurrencyItem>, String, Float>() {

    override fun buildModels(
        currencyList: List<CurrencyItem>?,
        currentCurrency: String,
        volume: Float
    ) {
        currencyList?.forEach { currency ->
            currencyItemView {
                id(currency.code)
                defaultCurrency(currentCurrency == currency.code)
                volume(volume)
                currencyModel(currency)
                onCurrencyChanged(onCurrencyChangedCallback)
                onVolumeChanged(onVolumeChangedCallback)
            }
        }
    }

    fun clearCallbacks() {
        onCurrencyChangedCallback = null
        onVolumeChangedCallback = null
    }
}
