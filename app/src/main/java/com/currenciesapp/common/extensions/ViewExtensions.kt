package com.currenciesapp.common.extensions

import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged

fun View.hideKeyboard() =
    context.inputMethodManager.hideSoftInputFromWindow(windowToken, Int.zero)

fun EditText.onTextChangedTextWatcher(callback: (String) -> Unit) =
    doOnTextChanged { text, _, _, _ ->
        if (hasFocus()) callback.invoke(text.toString())
    }
