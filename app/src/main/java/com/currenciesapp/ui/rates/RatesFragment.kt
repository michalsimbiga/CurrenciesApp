package com.currenciesapp.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.currenciesapp.R
import com.currenciesapp.common.doNothing
import com.currenciesapp.common.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_rates.*

class RatesFragment : BaseFragment() {

    private val viewModel: RatesViewModel by fragmentViewModel()

    private val epoxyController: RatesEpoxyController by lazy {
        RatesEpoxyController()
    }

    override fun invalidate() = withState(viewModel) { state ->
        if (state.currencyList is Success) epoxyController.setData(state.currencyList.invoke())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_rates, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ratesRecycler.setController(epoxyController)

        super.onViewCreated(view, savedInstanceState)
    }
}