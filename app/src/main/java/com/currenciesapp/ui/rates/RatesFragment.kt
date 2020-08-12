package com.currenciesapp.ui.rates

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.UniqueOnly
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.currenciesapp.R
import com.currenciesapp.UPDATE_TIME_1_SEC
import com.currenciesapp.common.doNothing
import com.currenciesapp.common.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_rates.*

class RatesFragment : BaseFragment() {

    private val viewModel: RatesViewModel by fragmentViewModel()

    private lateinit var handler: Handler

    private val epoxyController: RatesEpoxyController by lazy {
        RatesEpoxyController(
            onCurrencyChangedCallback = ::onCurrencySelected,
            onVolumeChangedCallback = ::onCurrencyRateChanged
        )
    }

    private val updateRatesTask = object : Runnable {
        override fun run() {
            viewModel.updateRates()
            handler.postDelayed(this, UPDATE_TIME_1_SEC)
        }
    }

    private fun onCurrencySelected(currencyName: String) =
        viewModel.setNewBaseCurrency(currencyName)

    private fun onCurrencyRateChanged(currencyRate: Float) =
        viewModel.setNewRate(currencyRate)

    private fun updateRecycler() = withState(viewModel) { state ->
        with(state) {
            epoxyController.setData(
                currencyList.invoke(),
                currentCurrency.invoke(),
                currentRate.invoke()
            )
        }
    }

    override fun invalidate() = doNothing

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_rates, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ratesRecycler.setController(epoxyController)

        handler = Handler(Looper.getMainLooper())

        viewModel.asyncSubscribe(
            owner = viewLifecycleOwner,
            asyncProp = RatesViewState::currencyList,
            deliveryMode = UniqueOnly(subscriptionId = "SubscribtioIn"),
            onSuccess = { updateRecycler() },
            onFail = {}
        )

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

    override fun onDestroyView() {
        super.onDestroyView()

        onRecyclerViewDetached(ratesRecycler)
    }

    override fun onDestroy() {
        super.onDestroy()

        epoxyController.clearCallbacks()
    }
}