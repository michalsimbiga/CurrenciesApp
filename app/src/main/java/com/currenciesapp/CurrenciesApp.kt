package com.currenciesapp

import android.app.Application
import com.blongho.country_data.World
import com.currenciesapp.common.lifecycleAnalytics.ActivityAnalyticsCallbacks
import com.currenciesapp.common.lifecycleAnalytics.ActivityAnalyticsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import timber.log.Timber

class CurrenciesApp : Application() {

    private val activityLifecycleCallbacks by lazy {
        ActivityAnalyticsCallbacks(ActivityAnalyticsImpl())
    }

    override fun onCreate() {
        super.onCreate()

        initKoin()
        setupLifecycleCallbacks()
        setupTimber()
        setupWorldLibrary()
    }

    private fun initKoin() = startKoin {
        androidContext(this@CurrenciesApp)
        androidLogger()
    }

    private fun setupLifecycleCallbacks() =
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupWorldLibrary(){
        World.init(applicationContext)
    }

    override fun onTerminate() {
        stopKoin()
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)

        super.onTerminate()
    }
}
