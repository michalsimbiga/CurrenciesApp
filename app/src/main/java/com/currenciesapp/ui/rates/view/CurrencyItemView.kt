package com.currenciesapp.ui.rates.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.currenciesapp.R
import com.currenciesapp.common.extensions.zero
import com.currenciesapp.model.CurrencyItem
import com.mynameismidori.currencypicker.ExtendedCurrency
import kotlinx.android.synthetic.main.fragment_rates_currency_item.view.*
import timber.log.Timber

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CurrencyItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = Int.zero
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.fragment_rates_currency_item, this)
    }

    @ModelProp
    fun setCurrencyItem(currency: CurrencyItem) {

        currencyName.text = currency.code
        currencyFullName.text = currency.fullName
        currencyRate.setText(currency.rate.toString())

        val flag = ExtendedCurrency.getCurrencyByISO(currency.code)

        Timber.i("TESTING currency ${flag}")

        currencyFlag.setImageResource(flag.flag)
//        currencyFlag.setImageResource(flagHelper)
//
//        Glide.with(context)
//            .load(flag)
//            .centerCrop()
//            .into(currencyFlag)
    }
}
