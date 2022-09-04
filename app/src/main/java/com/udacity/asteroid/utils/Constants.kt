package com.udacity.asteroid.utils

object Constants {


    //API
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val NASA_API_BASE_URL = "https://api.nasa.gov/"
    const val NASA_API_KEY = "cprKsnleJbl7dk4UnFpzmUIgDMnXViXg3QSEDE89"
    const val IMAGE_MEDIA_TYPE = "image"

    //Database
    const val ASTEROID_TABLE_NAME = "asteroid_table"
    const val PICTURE_OF_DAY_TABLE_NAME = "picture_of_day_table"
    const val DATABASE_NAME = "asteroid_db"

    //Worker
    const val REFRESH_WORKER_WORK_NAME = "RefreshAsteroidData"
    const val DELETE_WORKER_WORK_NAME = "DeleteAsteroidData"


}