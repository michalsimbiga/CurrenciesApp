package com.currenciesapp.common.ui

import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.currenciesapp.common.doNothing
import com.currenciesapp.common.extensions.inputMethodManager
import com.currenciesapp.common.extensions.zero

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

    fun navigate(action: NavDirections) = findNavController().navigate(action)

    fun navigate(@IdRes resId: Int) = findNavController().navigate(resId)

    fun hideKeyboard() =
        requireContext().inputMethodManager.hideSoftInputFromWindow(view?.windowToken, Int.zero)

}
