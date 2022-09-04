package com.udacity.asteroid.data.network

import com.udacity.asteroid.utils.Constants
import com.udacity.asteroid.data.pojo.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {

    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate:String,
        @Query("api_key") apiKey : String = Constants.NASA_API_KEY)
    :Deferred<ResponseBody>

    @GET("planetary/apod")
    fun getPictureOfDay(
        @Query("api_key") apiKey: String = Constants.NASA_API_KEY
    ): Deferred<PictureOfDay>


}