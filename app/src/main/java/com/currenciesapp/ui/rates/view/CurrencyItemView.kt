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

    var volume by Delegates.notNull<Float>()
        @ModelProp set

    var isDefaultCurrency by Delegates.notNull<Boolean>()
        @ModelProp set

    init {
        inflate(context, R.layout.fragment_rates_currency_item, this)
    }

    private fun calculatePrice() = currencyModel.rate * volume

    @AfterPropsSet
    fun setupView() = executeWithoutUserManipulation {
        if (isDefaultCurrency.not()) currencyRate.setText(calculatePrice().toString())

        with(currencyModel) {
            currencyName.text = code
            currencyFullName.text = fullName
            val extendedCurrency = ExtendedCurrency.getCurrencyByISO(code)
            currencyFlag.setImageResource(extendedCurrency.flag)
        }

        setImeButtonAction()
    }

    private fun setImeButtonAction() {
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

    private fun updateVolume() = currencyRate.setText(currencyRate.text)

    @CallbackProp
    fun onCurrencyChanged(currencyChangedCallback: ((String) -> Unit)?) {
        currencyRate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) currencyChangedCallback?.invoke(currencyModel.code)
                .also { updateVolume() }
        }
    }

    @CallbackProp
    fun onVolumeChanged(onVolumeChangedCallback: ((Float) -> Unit)?) =
        currencyRate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = doNothing
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = doNothing
            override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (userManipulation && newText.isNullOrBlank().not()) {
                    onVolumeChangedCallback?.invoke(newText.toString().toFloat())
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
        currencyRate.setText(volume.toString())
    }
}
