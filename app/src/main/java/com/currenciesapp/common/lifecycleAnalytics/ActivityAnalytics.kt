package com.currenciesapp.common.lifecycleAnalytics

import android.app.Activity

interface ActivityAnalytics {
    fun activityCreated(activity: Activity)
    fun activityResumed(activity: Activity)
    fun activityStarted(activity: Activity)
    fun activityPaused(activity: Activity)
    fun activityStopped(activity: Activity)
    fun activitySaveInstanceState(activity: Activity)
    fun activityDestroyed(activity: Activity)
}
