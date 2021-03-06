package com.currenciesapp.ui.rates.view

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.currenciesapp.R
import com.currenciesapp.common.extensions.comma
import com.currenciesapp.common.extensions.dot
import com.currenciesapp.common.extensions.hideKeyboard
import com.currenciesapp.common.extensions.onTextChangedTextWatcher
import com.currenciesapp.common.extensions.zero
import com.currenciesapp.model.CurrencyItem
import com.mynameismidori.currencypicker.ExtendedCurrency
import kotlinx.android.synthetic.main.fragment_rates_currency_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CurrencyItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = Int.zero
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val userManipulation
        get() = currencyRate.hasFocus()

    lateinit var currencyModel: CurrencyItem
        @ModelProp set

    var volume by Delegates.notNull<Double>()
        @ModelProp set

    init {
        inflate(context, R.layout.fragment_rates_currency_item, this)
    }

    private fun calculatePrice() = (currencyModel.rate * volume)

    @AfterPropsSet
    fun setupView() {

        // TODO FIND OUT WHY IT DOESNT WORK WITHOUT GLOBAL SCOPE FOR OFFLINE MODE (IF DATA IS PRESENT IN DB)
        if (userManipulation.not()) GlobalScope.launch(Dispatchers.Main) {
            currencyRate.setText(
                calculatePrice().toString()
            )
        }

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

    private fun updateVolume() {
        currencyRate.text = currencyRate.text
    }

    @CallbackProp
    fun onCurrencyChanged(currencyChangedCallback: ((String) -> Unit)?) {
        currencyRate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                currencyChangedCallback?.invoke(currencyModel.code)
                updateVolume()
            }
        }
    }

    @CallbackProp
    fun onVolumeChanged(onVolumeChangedCallback: ((Double) -> Unit)?) {
        currencyRate.onTextChangedTextWatcher { newVolume ->
            if (isVolumeValid(newVolume)) {
                onVolumeChangedCallback?.invoke(newVolume.toDouble())
            }
        }
    }

    private fun isVolumeValid(text: CharSequence?) =
        text.isNullOrBlank().not() && text != String.dot && text?.trim() != String.comma

    @OnViewRecycled
    fun onViewRecycled() {
        clearFocus()
        currencyRate.setText(volume.toString())
    }
}
