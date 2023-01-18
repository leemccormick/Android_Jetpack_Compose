package com.leemccormick.jetweatherforcast.repository

import com.leemccormick.jetweatherforcast.data.WeatherDao
import com.leemccormick.jetweatherforcast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// repository == Source of Truth --> To connect to database and dependency injection

// Inject and Mirror the Dao, this is where we actually get information
class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)

    suspend fun getFavById(city: String) = weatherDao.getFavById(city)
}