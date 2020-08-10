package com.currenciesapp.common.lifecycleAnalytics

import android.app.Activity
import timber.log.Timber

class ActivityAnalyticsImpl :
    ActivityAnalytics {

    override fun activityCreated(activity: Activity) =
        Timber.tag(activity.javaClass.simpleName).v("on_create")

    override fun activityResumed(activity: Activity) =
        Timber.tag(activity.javaClass.simpleName).v("on_resume")

    override fun activityPaused(activity: Activity) =
        Timber.tag(activity.javaClass.simpleName).v("on_pause")

    override fun activityDestroyed(activity: Activity) =
        Timber.tag(activity.javaClass.simpleName).v("on_destroy")

    override fun activityStarted(activity: Activity) =
        Timber.tag(activity.javaClass.simpleName).v("on_start")

    override fun activityStopped(activity: Activity) =
        Timber.tag(activity.javaClass.simpleName).v("on_stop")

    override fun activitySaveInstanceState(activity: Activity) =
        Timber.tag(activity.javaClass.simpleName).v("on_save_instance_state")
}
