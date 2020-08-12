package com.currenciesapp.common.ui

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.currenciesapp.common.doNothing
import com.currenciesapp.ui.main.MainActivity

abstract class BaseFragment : BaseMvRxFragment() {

    fun onRecyclerViewDetached(view: RecyclerView) {
        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                view.adapter = null
            }

            override fun onViewAttachedToWindow(v: View?) =
                doNothing
        })
    }

    fun showSnackbar(@StringRes text: Int, durationIndefinite: Boolean) =
        (activity as MainActivity).showSnackbar(text, durationIndefinite)

    fun hideSnack() = (activity as MainActivity).hideSnack()
}
