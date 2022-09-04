package com.udacity.asteroid.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.udacity.asteroid.R
import com.udacity.asteroid.data.database.AsteroidDatabase
import com.udacity.asteroid.data.repositories.AsteroidRepository
import com.udacity.asteroid.utils.Constants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = AsteroidRepository(AsteroidDatabase.getDatabase(this).asteroidDao)
        val viewModel = ViewModelProvider(this , AsteroidViewModelFactory(repository))[AsteroidViewModel::class.java]

    }
}