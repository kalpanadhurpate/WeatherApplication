package me.kalpanadhurpate.weatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.kalpanadhurpate.weatherapp.data.api.NetworkService
import me.kalpanadhurpate.weatherapp.data.model.Latlon
import me.kalpanadhurpate.weatherapp.data.model.WeatherResponse
import me.kalpanadhurpate.weatherapp.di.WeatherApiKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val networkService: NetworkService,
    @WeatherApiKey private val apiKey: String,
) {

    fun getLatLon(city: String): Flow<List<Latlon>> {
        val limit = 5
        return flow {
            val response = networkService.getLatLong(city, apiKey, limit)
            emit(response)
        }
    }

    fun getWeatherForLatLon(lat: Double, lon: Double):Flow<WeatherResponse> {
        return flow {
            val response = networkService.getWeatherForLatLon(lat, lon, apiKey)
            emit(response)
        }

    }


}