package com.leemccormick.jetweatherforcast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leemccormick.jetweatherforcast.model.Favorite
import com.leemccormick.jetweatherforcast.model.Unit

// When added Unit table, add version so it will create another version of database
@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
