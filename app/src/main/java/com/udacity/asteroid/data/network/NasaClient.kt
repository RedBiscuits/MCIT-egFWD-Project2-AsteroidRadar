package com.udacity.asteroid.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroid.utils.Constants
import com.udacity.asteroid.data.pojo.Asteroid
import com.udacity.asteroid.data.pojo.PictureOfDay
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NasaClient {

     private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
     private val client =OkHttpClient()
          .newBuilder()
          .readTimeout(60 , TimeUnit.SECONDS)
          .connectTimeout(60 , TimeUnit.SECONDS)
          .build()

     private val retrofit = Retrofit.Builder()
          .baseUrl(Constants.NASA_API_BASE_URL)
          .addConverterFactory(MoshiConverterFactory.create(moshi))
          .addCallAdapterFactory(CoroutineCallAdapterFactory())
          .client(client)
          .build()

     val nasaInterface: NasaAPI = retrofit.create(NasaAPI::class.java)




}