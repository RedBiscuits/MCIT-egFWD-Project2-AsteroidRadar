package com.udacity.asteroid.database

import androidx.room.*
import com.udacity.asteroid.pojo.Asteroid
import com.udacity.asteroid.pojo.PictureOfDay
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
    fun getAsteroidsByCloseApproachDate(startDate: String, endDate: String): Flow<List<Asteroid>>

    @Query("SELECT * FROM ${Constants.ASTEROID_TABLE_NAME} " +
            "ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): Flow<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroids: Asteroid)

    @Query("DELETE FROM ${Constants.ASTEROID_TABLE_NAME} WHERE closeApproachDate < :today")
    fun deletePreviousDayAsteroids(today: String): Int

    /*
    * Pics
    * */
    @Query("SELECT * FROM ${Constants.PICTURE_OF_DAY_TABLE_NAME}")
    fun getPictureOfDay(): Flow<PictureOfDay>

    @Insert
    fun insertPicture(pictureOfDay: PictureOfDay)

    @Query("DELETE FROM ${Constants.PICTURE_OF_DAY_TABLE_NAME}")
    fun nukePicturesTable()
}