package com.udacity.asteroid.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroid.utils.Constants

@Entity(tableName = Constants.PICTURE_OF_DAY_TABLE_NAME)
data class PictureOfDay(
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    @PrimaryKey
    val url: String
)