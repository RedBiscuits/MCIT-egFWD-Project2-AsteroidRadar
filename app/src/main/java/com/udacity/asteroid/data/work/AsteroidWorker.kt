package com.udacity.asteroid.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroid.data.database.AsteroidDatabase
import com.udacity.asteroid.data.repositories.AsteroidRepository
import retrofit2.HttpException

class AsteroidWorker(context: Context , params: WorkerParameters)
    : CoroutineWorker(context , params) {
    private val dbDao = AsteroidDatabase.getDatabase(context).asteroidDao
    private val repository = AsteroidRepository(dbDao)
    override suspend fun doWork(): Result {
        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}