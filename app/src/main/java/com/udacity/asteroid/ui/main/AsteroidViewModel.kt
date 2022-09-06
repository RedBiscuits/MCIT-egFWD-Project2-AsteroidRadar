package com.udacity.asteroid.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroid.data.pojo.Asteroid
import com.udacity.asteroid.data.pojo.PictureOfDay
import com.udacity.asteroid.data.repositories.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsteroidViewModel(
    private val repository: AsteroidRepository
) : ViewModel() {



    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids:LiveData<List<Asteroid>>
        get() = _asteroids

    private val _allAsteroids = MutableLiveData<List<Asteroid>>()
    val allAsteroids:LiveData<List<Asteroid>>
        get() = _allAsteroids


    private fun getAsteroids() = viewModelScope.launch(Dispatchers.IO){
        var asteroids = repository.getAsteroids()
        var allAsteroids = repository.getAllAsteroids()
        if (asteroids.isEmpty()){
            repository.refreshAsteroids()
            asteroids = repository.getAsteroids()
            allAsteroids =repository.getAllAsteroids()
        }
        asteroids?.let {
            _asteroids.postValue(it)
        }
        allAsteroids?.let {
            _allAsteroids.postValue(it)
        }
        _loading.postValue(false)
    }

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay>
        get() = _picture

    private fun getTodayPicture() = viewModelScope.launch(Dispatchers.IO){
        val picture = repository.getPictureOfDay()
        picture?.let {
        _picture.postValue(it)
        }
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
        get() = _loading

    init {
        viewModelScope.launch (Dispatchers.Default){
            _loading.postValue(true)
            withContext(Dispatchers.IO) {
                getTodayPicture()
            }
            viewModelScope.launch{
                getAsteroids()
            }
        }
    }

    private val _navigateToAsteroidDetail:MutableLiveData<Asteroid>? = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToAsteroidDetail!!.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail!!.value = null
    }
    fun getToday():String{
        return repository.getToday()
    }
}