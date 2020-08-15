package com.currenciesapp.ui.rates

import com.airbnb.epoxy.Typed2EpoxyController
import com.currenciesapp.model.RatesItem
import com.currenciesapp.ui.rates.view.currencyItemView

class RatesEpoxyController(
    private var onCurrencyChangedCallback: ((String) -> Unit)?,
    private var onVolumeChangedCallback: ((Double) -> Unit)?
) : Typed2EpoxyController<RatesItem, Double>() {

    override fun buildModels(
        currencyRates: RatesItem,
        volume: Double
    ) {
        currencyItemView {
            id(currencyRates.baseCurrency.code)
            defaultCurrency(true)
            volume(volume)
            currencyModel(currencyRates.baseCurrency)
            onCurrencyChanged(onCurrencyChangedCallback)
            onVolumeChanged(onVolumeChangedCallback)
        }

        currencyRates.rates.forEach { currency ->
            currencyItemView {
                id(currency.code)
                defaultCurrency(false)
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
