package com.udacity.asteroid.data.work


import android.app.Application
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.work.*
import com.udacity.asteroid.data.database.AsteroidDatabase
import com.udacity.asteroid.data.repositories.AsteroidRepository
import com.udacity.asteroid.utils.Constants
import kotlinx.coroutines.*

import java.util.concurrent.TimeUnit

class WorkManagerScheduler : Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            setupWorker()
        }
    }

    private fun setupWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        Log.d("meow" , "In setup method")

        val refreshRequest = PeriodicWorkRequestBuilder<AsteroidRefreshWork>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            Constants.REFRESH_WORKER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshRequest
        )

        val deleteRequest = PeriodicWorkRequestBuilder<AsteroidDeleteWork>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            Constants.DELETE_WORKER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            deleteRequest
        )

    }
}