package com.currenciesapp.common.lifecycleAnalytics

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityAnalyticsCallbacks(private val activityAnalytics: ActivityAnalytics) :
    Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) =
        activityAnalytics.activityCreated(activity)

    override fun onActivityPaused(activity: Activity) = activityAnalytics.activityPaused(activity)

    override fun onActivityStarted(activity: Activity) = activityAnalytics.activityStarted(activity)

    override fun onActivityDestroyed(activity: Activity) =
        activityAnalytics.activityDestroyed(activity)

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) =
        activityAnalytics.activitySaveInstanceState(activity)

    override fun onActivityStopped(activity: Activity) = activityAnalytics.activityStopped(activity)

    override fun onActivityResumed(activity: Activity) = activityAnalytics.activityResumed(activity)
}
