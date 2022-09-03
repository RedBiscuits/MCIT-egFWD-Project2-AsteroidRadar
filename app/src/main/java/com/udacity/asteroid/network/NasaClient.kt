package com.udacity.asteroid.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroid.utils.Constants
import com.udacity.asteroid.pojo.Asteroid
import com.udacity.asteroid.pojo.PictureOfDay
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NasaClient {

     private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

     private val retrofit = Retrofit.Builder()
     .baseUrl(Constants.NASA_API_BASE_URL)
     .addConverterFactory(MoshiConverterFactory.create(moshi))
     .addCallAdapterFactory(CoroutineCallAdapterFactory())
     .build()

     private val nasaInterface = retrofit.create(NasaAPI::class.java)

     suspend fun getAsteroids(startDate : String): ArrayList<Asteroid>{

          var asteroids: ArrayList<Asteroid>
          withContext(Dispatchers.IO){
               val response = nasaInterface.getAsteroids(startDate).await()
               asteroids = parseAsteroidsJsonResult(JSONObject(response.string()))
          }
          return asteroids
     }

     suspend fun getPictureOfDay(): PictureOfDay? {
          var pictureOfDay: PictureOfDay
          withContext(Dispatchers.IO) {
               pictureOfDay = nasaInterface.getPictureOfDay().await()
          }
          if (pictureOfDay.mediaType == Constants.IMAGE_MEDIA_TYPE) {
               return pictureOfDay
          }
          return null
     }

}