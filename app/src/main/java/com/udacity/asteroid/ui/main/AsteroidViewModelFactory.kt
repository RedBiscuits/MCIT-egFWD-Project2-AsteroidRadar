package com.udacity.asteroid.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroid.data.repositories.AsteroidRepository

class AsteroidViewModelFactory constructor(private val repository: AsteroidRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AsteroidViewModel::class.java)) {
            AsteroidViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
