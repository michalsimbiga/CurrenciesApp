package com.currenciesapp.common.lifecycleAnalytics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentAnalyticsCallbacks(private val fragmentAnalytics: FragmentAnalytics) :
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) = fragmentAnalytics.fragmentCreated(fragment)

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) =
        fragmentAnalytics.fragmentPaused(fragment)

    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) =
        fragmentAnalytics.fragmentStarted(fragment)

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) =
        fragmentAnalytics.fragmentDestroyed(fragment)

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) =
        fragmentAnalytics.fragmentStopped(fragment)

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        view: View,
        bundle: Bundle?
    ) = fragmentAnalytics.fragmentViewCreated(fragment)

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) =
        fragmentAnalytics.fragmentViewDestroyed(fragment)

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) =
        fragmentAnalytics.fragmentResumed(fragment)
}
