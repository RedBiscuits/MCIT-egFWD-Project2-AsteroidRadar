package com.udacity.asteroid.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroid.R
import com.udacity.asteroid.data.database.AsteroidDatabase
import com.udacity.asteroid.data.repositories.AsteroidRepository
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = AsteroidRepository(AsteroidDatabase.getDatabase(this).asteroidDao)
        val viewModel = ViewModelProvider(this , AsteroidViewModelFactory(repository))[AsteroidViewModel::class.java]
        GlobalScope.launch (Dispatchers.IO) {

//            repository.refreshAsteroids()
            viewModel.getAsteroids()
        }

        viewModel.asteroids.observe(this){
            for (i in it){

                Log.d("meow" , i.toString())
            }
        }
    }
}