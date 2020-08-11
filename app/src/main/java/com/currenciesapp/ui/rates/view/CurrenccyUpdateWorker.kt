package com.currenciesapp.ui.rates.view

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CurrenccyUpdateWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val workCallback: (() -> Unit)? = null

    override fun doWork(): Result {
        workCallback?.invoke()
        return Result.success()
    }
}