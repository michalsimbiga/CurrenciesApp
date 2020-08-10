package com.currenciesapp.ui.rates.view

import android.content.Context
import android.icu.util.Currency
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.blongho.country_data.World
import com.bumptech.glide.Glide
import com.currenciesapp.R
import com.currenciesapp.model.CurrencyItem
import kotlinx.android.synthetic.main.fragment_rates_currency_item.view.*
import timber.log.Timber

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CurrencyItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.fragment_rates_currency_item, this)
    }

    @ModelProp
    fun setCurrencyItem(currencyItem: CurrencyItem) {
        val currency = java.util.Currency.getInstance(currencyItem.name)

        val flagHelper = World.getFlagOf(currency.numericCode)
        Timber.i("TESTING currencyFlag $flagHelper")

        currencyName.text = currency.currencyCode
        currencyFullName.text = currency.displayName
        currencyRate.setText(currencyItem.rate.toString())

        currencyFlag.setImageResource(flagHelper)
//
//        Glide.with(context)
//            .load(flagHelper)
//            .centerCrop()
//            .into(currencyFlag)
    }
}
