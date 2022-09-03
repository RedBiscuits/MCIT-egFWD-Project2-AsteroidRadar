package com.udacity.asteroid.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.udacity.asteroid.R
import com.udacity.asteroid.network.NasaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch (Dispatchers.IO) {
        Log.d("meow" , NasaClient.getPictureOfDay()!!.title)
        }
    }
}