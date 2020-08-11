package com.currenciesapp.ui.rates.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.*
import com.currenciesapp.R
import com.currenciesapp.common.doNothing
import com.currenciesapp.common.extensions.hideKeyboard
import com.currenciesapp.common.extensions.zero
import com.currenciesapp.model.CurrencyItem
import com.mynameismidori.currencypicker.ExtendedCurrency
import kotlinx.android.synthetic.main.fragment_rates_currency_item.view.*
import kotlin.properties.Delegates

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CurrencyItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = Int.zero
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var userManipulation = true

    lateinit var currencyModel: CurrencyItem
        @ModelProp set

    var rate by Delegates.notNull<Float>()
        @ModelProp set

    var isDefaultCurrency by Delegates.notNull<Boolean>()
        @ModelProp set

    var isFocusedCurrency by Delegates.notNull<Boolean>()
        @ModelProp set

    init {
        inflate(context, R.layout.fragment_rates_currency_item, this)
    }

    private fun calculatePrice() = currencyModel.rate * rate

    @AfterPropsSet
    fun setupView() = executeWithoutUserManipulation {
        currencyName.text = currencyModel.code
        currencyFullName.text = currencyModel.fullName
        if (isDefaultCurrency.not()) currencyRate.setText(calculatePrice().toString())

        if (isFocusedCurrency) currencyRate.requestFocus()

        val extendedCurrency = ExtendedCurrency.getCurrencyByISO(currencyModel.code)
        currencyFlag.setImageResource(extendedCurrency.flag)

        currencyRate.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE,
                EditorInfo.IME_ACTION_GO,
                EditorInfo.IME_ACTION_SEND -> true.also {
                    clearFocus()
                    hideKeyboard()
                }
                else -> false
            }
        }
    }

    @CallbackProp
    fun onCurrencyNameChanged(callback: ((String) -> Unit)?) {
        currencyRate.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) callback?.invoke(currencyModel.code)
        }
    }

    @CallbackProp
    fun onCurrencyRateChanged(newRateCallback: ((Float) -> Unit)?) =
        currencyRate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = doNothing
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = doNothing

            override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (userManipulation && newText.isNullOrBlank().not()) {
                    newRateCallback?.invoke(newText.toString().toFloat())
                }
            }
        })

    private fun executeWithoutUserManipulation(call: () -> Unit) {
        userManipulation = false
        call()
        userManipulation = true
    }

    @OnViewRecycled
    fun onViewRecycled() {
        clearFocus()
    }
}
