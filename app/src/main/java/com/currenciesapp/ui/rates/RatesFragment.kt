package com.currenciesapp.ui.rates

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.currenciesapp.R
import com.currenciesapp.common.doNothing
import com.currenciesapp.common.ui.BaseFragment
import com.currenciesapp.ui.rates.view.CurrenccyUpdateWorker
import kotlinx.android.synthetic.main.fragment_rates.*
import java.util.concurrent.TimeUnit

class RatesFragment : BaseFragment() {

    private val viewModel: RatesViewModel by fragmentViewModel()

    private lateinit var handler : Handler

    private val epoxyController: RatesEpoxyController by lazy {
        RatesEpoxyController()
    }

    private val updateRatesTask = object : Runnable {
        override fun run() {
            viewModel.updateRates()
            handler.postDelayed(this, 1000)
        }
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

        handler = Handler(Looper.getMainLooper())

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        handler.post(updateRatesTask)
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(updateRatesTask)
    }
}