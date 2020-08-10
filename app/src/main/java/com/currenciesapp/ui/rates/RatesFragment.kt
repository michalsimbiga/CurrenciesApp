package com.currenciesapp.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.currenciesapp.R
import com.currenciesapp.common.doNothing
import com.currenciesapp.common.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_rates.*

class RatesFragment : BaseFragment() {

    private val viewModel: RatesViewModel by fragmentViewModel()

    override fun invalidate() = doNothing

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_rates, container, false).apply {
    }


}