package com.currenciesapp.common.ui

import androidx.lifecycle.ViewModelStoreOwner
import com.airbnb.mvrx.ActivityViewModelContext
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

/**
 * A [MvRxViewModelFactory] which makes it easy to create instances of a ViewModel
 * using koin. This class should be implemented by the companion object
 * of every ViewModel which you would want to use with koin `viewModel {}` definition inside a module
 *
 * @param viewModelClass The [Class] of the ViewModel being requested for creation
 *
 * Example:
 *
 * class MyViewModel  constructor(...): MvRxViewModel<MyState>(...) {

 *   companion object : KoinMvRxViewModelFactory<MyViewModel, MyState>(MyViewModel::class.java)
 *
 * }
 */
abstract class KoinMvRxViewModelFactory<VM : BaseMvRxViewModel<S>, S : MvRxState>(
    private val viewModelClass: KClass<out BaseMvRxViewModel<S>>
) : MvRxViewModelFactory<VM, S> {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun create(viewModelContext: ViewModelContext, state: S): VM? {
        val lifecycleOwner = when (viewModelContext) {
            is FragmentViewModelContext -> viewModelContext.fragment
            is ActivityViewModelContext -> viewModelContext.activity
        } as ViewModelStoreOwner

        return createViewModel(lifecycleOwner, state)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <VM : BaseMvRxViewModel<S>, S : MvRxState> createViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        state: S
    ): VM = viewModelStoreOwner.getViewModel(
        clazz = viewModelClass,
        parameters = { parametersOf(state) }) as VM
}
