package com.udacity.asteroid.data.repositories

import com.udacity.asteroid.data.database.AsteroidDao
import com.udacity.asteroid.data.network.NasaClient
import com.udacity.asteroid.data.network.parseAsteroidsJsonResult
import com.udacity.asteroid.data.pojo.Asteroid
import com.udacity.asteroid.data.pojo.PictureOfDay
import com.udacity.asteroid.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val dbDao: AsteroidDao) {

    suspend fun refreshAsteroids(): ArrayList<Asteroid>{

        var asteroids: ArrayList<Asteroid>
        withContext(Dispatchers.IO){
            val response = NasaClient.nasaInterface.getAsteroids(getToday()).await()
            asteroids = parseAsteroidsJsonResult(JSONObject(response.string()))

            for(astr in asteroids){
                dbDao.insertAsteroid(astr)
            }
        }
        return asteroids
    }

    fun getAsteroids() : List<Asteroid>{
        return dbDao.getAsteroidsByCloseApproachDate(getToday(),getNextWeak())
    }

    fun deleteOutdatedAsteroids(){
        dbDao.deletePreviousDayAsteroids(getToday())
    }

    suspend fun getPictureOfDay() : PictureOfDay{
        return NasaClient.nasaInterface.getPictureOfDay().await()

    }

    fun getAllAsteroids (): List<Asteroid>{
        return dbDao.getAllAsteroids()
    }


    fun getToday(): String {
        val calendar = Calendar.getInstance()
        return formatDate(calendar.time)
    }

    private fun getNextWeak(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        return formatDate(calendar.time)
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }

}