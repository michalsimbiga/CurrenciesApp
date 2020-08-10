package com.currenciesapp.common.lifecycleAnalytics

import androidx.fragment.app.Fragment

interface FragmentAnalytics {
    fun fragmentCreated(fragment: Fragment)
    fun fragmentViewCreated(fragment: Fragment)
    fun fragmentResumed(fragment: Fragment)
    fun fragmentPaused(fragment: Fragment)
    fun fragmentDestroyed(fragment: Fragment)
    fun fragmentViewDestroyed(fragment: Fragment)
    fun fragmentStarted(fragment: Fragment)
    fun fragmentStopped(fragment: Fragment)
}
