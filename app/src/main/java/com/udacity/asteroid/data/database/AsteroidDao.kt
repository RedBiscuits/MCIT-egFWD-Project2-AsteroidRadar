package com.udacity.asteroid.data.database

import androidx.room.*
import com.udacity.asteroid.data.pojo.Asteroid
import com.udacity.asteroid.data.pojo.PictureOfDay
import com.udacity.asteroid.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    /*
    * Asteroids
    * */
    @Query("SELECT * FROM ${Constants.ASTEROID_TABLE_NAME} " +
            "WHERE closeApproachDate >= :startDate " +
            "AND closeApproachDate <= :endDate " +
            "ORDER BY closeApproachDate ASC")
    fun getAsteroidsByCloseApproachDate(startDate: String, endDate: String): List<Asteroid>

    @Query("SELECT * FROM ${Constants.ASTEROID_TABLE_NAME} " +
            "ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroid(vararg asteroid: Asteroid)

    @Query("DELETE FROM ${Constants.ASTEROID_TABLE_NAME} WHERE closeApproachDate < :today")
    fun deletePreviousDayAsteroids(today: String): Int

}