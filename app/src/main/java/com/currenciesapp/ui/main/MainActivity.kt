package com.currenciesapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.currenciesapp.R
import com.currenciesapp.di.appModule
import com.currenciesapp.di.dataModule
import com.currenciesapp.di.domainModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainActivity : AppCompatActivity() {


    init {
        loadModules()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        unloadModules()

        super.onDestroy()
    }

    private fun loadModules() =
        loadKoinModules(listOf(domainModule, dataModule, appModule))

    private fun unloadModules() =
        unloadKoinModules(listOf(domainModule, dataModule, appModule))
}