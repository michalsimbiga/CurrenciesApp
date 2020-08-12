package com.currenciesapp.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.currenciesapp.R
import com.currenciesapp.di.appModule
import com.currenciesapp.di.dataModule
import com.currenciesapp.di.domainModule
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainActivity : AppCompatActivity() {

    private var snackbar: Snackbar? = null

    init {
        loadModules()
    }

    fun showSnackbar(@StringRes text: Int, durationIndefinite: Boolean) {
        snackbar = Snackbar.make(
            mainView,
            text,
            if (durationIndefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        )
        snackbar?.show()
    }

    fun hideSnack() = snackbar?.dismiss()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        snackbar?.show()
        val navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onDestroy() {
        unloadModules()
        snackbar?.dismiss()

        super.onDestroy()
    }

    private fun loadModules() =
        loadKoinModules(listOf(domainModule, dataModule, appModule))

    private fun unloadModules() =
        unloadKoinModules(listOf(domainModule, dataModule, appModule))
}