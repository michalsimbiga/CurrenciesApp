package com.currenciesapp.common.lifecycleAnalytics

import androidx.fragment.app.Fragment
import timber.log.Timber

class FragmentAnalyticsImpl :
    FragmentAnalytics {

    override fun fragmentCreated(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_create")

    override fun fragmentViewCreated(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_create_view")

    override fun fragmentResumed(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_resume")

    override fun fragmentPaused(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_pause")

    override fun fragmentDestroyed(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_destroy")

    override fun fragmentViewDestroyed(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_destroy_view")

    override fun fragmentStarted(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_start")

    override fun fragmentStopped(fragment: Fragment) =
        Timber.tag(fragment.javaClass.simpleName).v("on_stop")
}
