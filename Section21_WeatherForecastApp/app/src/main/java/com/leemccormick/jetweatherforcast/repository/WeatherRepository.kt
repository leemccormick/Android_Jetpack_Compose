package com.leemccormick.jetweatherforcast.repository

import android.util.Log
import com.leemccormick.jetweatherforcast.data.DataOrException
import com.leemccormick.jetweatherforcast.model.Weather
import com.leemccormick.jetweatherforcast.model.WeatherObject
import com.leemccormick.jetweatherforcast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception) {
            Log.d("REX", "getWeather : $e")
            return DataOrException(e = e)
        }

        Log.d("Response", "getWeather : $response")

        return DataOrException(data = response)
    }
}