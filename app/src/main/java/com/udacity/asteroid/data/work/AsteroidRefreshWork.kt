package com.udacity.asteroid.data.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroid.data.database.AsteroidDatabase
import com.udacity.asteroid.data.repositories.AsteroidRepository
import retrofit2.HttpException

class AsteroidRefreshWork(context: Context, params: WorkerParameters)
    : CoroutineWorker(context , params) {

    private val repository =
        AsteroidRepository(AsteroidDatabase.getDatabase(context).asteroidDao)

    override suspend fun doWork(): Result {
        return try {
            Log.d("meow" , "Getting data..")
            repository.refreshAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}