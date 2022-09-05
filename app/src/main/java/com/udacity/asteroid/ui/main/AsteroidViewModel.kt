package com.udacity.asteroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroid.data.pojo.Asteroid
import com.udacity.asteroid.data.pojo.PictureOfDay
import com.udacity.asteroid.data.repositories.AsteroidRepository
import kotlinx.coroutines.launch

class AsteroidViewModel(
    private val repository: AsteroidRepository
) : ViewModel() {



    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids:LiveData<List<Asteroid>>
        get() = _asteroids

    fun getAsteroids() = viewModelScope.launch{
        _asteroids.postValue(repository.getAsteroids())
    }


    private val _picture = MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay>
        get() = _picture

    fun getTodayPicture() = viewModelScope.launch{
        _picture.postValue(repository.getPictureOfDay())
    }

    init {
        viewModelScope.launch {
//            repository.refreshPictureOfDay()
            getTodayPicture()
            getAsteroids()
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

}