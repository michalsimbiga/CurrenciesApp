package com.currenciesapp.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airbnb.epoxy.Typed2EpoxyController
import com.currenciesapp.model.RatesItem
import com.currenciesapp.ui.rates.view.currencyItemView

class RatesEpoxyController(
    private var onCurrencyChangedCallback: ((String) -> Unit)?,
    private var onVolumeChangedCallback: ((Double) -> Unit)?
) : Typed2EpoxyController<RatesItem, Double>() {

    private val _volume: MutableLiveData<Double> = MutableLiveData(1.0)
    private val volume: LiveData<Double> = _volume

    override fun buildModels(
        currencyRates: RatesItem,
        volume: Double
    ) {
        (listOf(currencyRates.baseCurrency) + currencyRates.rates).forEach { currency ->
            currencyItemView {
                id(currency.code)
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
