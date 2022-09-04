package com.udacity.asteroid.data.repositories

import android.util.Log
import com.udacity.asteroid.data.database.AsteroidDao
import com.udacity.asteroid.data.network.NasaClient
import com.udacity.asteroid.data.network.parseAsteroidsJsonResult
import com.udacity.asteroid.data.pojo.Asteroid
import com.udacity.asteroid.data.pojo.PictureOfDay
import com.udacity.asteroid.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AsteroidRepository(private val dbDao: AsteroidDao) {

    suspend fun refreshAsteroids(): ArrayList<Asteroid>{

        var asteroids: ArrayList<Asteroid>
        withContext(Dispatchers.IO){
            Log.d("meow" ,"Getting data...")
            val response = NasaClient.nasaInterface.getAsteroids(getToday()).await()
            asteroids = parseAsteroidsJsonResult(JSONObject(response.string()))
            Log.d("meow" ,"Inserting data..." )

            for(astr in asteroids){
                Log.d("meow", "inserting item...")
                dbDao.insertAsteroid(astr)
                Log.d("item" ,astr.toString() )

            }
        }
        return asteroids
    }

    suspend fun getAsteroids() : List<Asteroid>{
        return dbDao.getAsteroidsByCloseApproachDate(getToday(),getNextWeak())
    }

    suspend fun deleteOutdatedAsteroids(){
        dbDao.deletePreviousDayAsteroids(getToday())
    }

    suspend fun getPictureOfDay(): PictureOfDay? {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = NasaClient.nasaInterface.getPictureOfDay().await()
        }
        if (pictureOfDay.mediaType == Constants.IMAGE_MEDIA_TYPE) {
            return pictureOfDay
        }
        return null
    }

    fun getToday(): String {
        val calendar = Calendar.getInstance()
        Log.d("time" ,formatDate(calendar.time).toString() )
        return formatDate(calendar.time)
    }

    private fun getNextWeak(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        Log.d("time" ,formatDate(calendar.time).toString() )
        return formatDate(calendar.time)
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }
}