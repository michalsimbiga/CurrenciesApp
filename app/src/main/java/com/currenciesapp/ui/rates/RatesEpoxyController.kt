package com.currenciesapp.ui.rates

import com.airbnb.epoxy.TypedEpoxyController
import com.currenciesapp.model.CurrencyItem
import com.currenciesapp.ui.rates.view.currencyItemView

class RatesEpoxyController() : TypedEpoxyController<List<CurrencyItem>>() {

    override fun buildModels(currencyList: List<CurrencyItem>?) {

        currencyList?.forEachIndexed { index, currency ->
            currencyItemView {
                id(index)
                currencyItem(currency)
            }
        }
    }

}