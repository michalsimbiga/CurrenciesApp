package com.currenciesapp.common.extensions

import android.view.View

fun View.hideKeyboard() =
    context.inputMethodManager.hideSoftInputFromWindow(windowToken, Int.zero)
