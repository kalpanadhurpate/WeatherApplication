package me.kalpanadhurpate.weatherapp.data.api

import kotlinx.coroutines.flow.Flow
import me.kalpanadhurpate.weatherapp.data.model.Latlon
import me.kalpanadhurpate.weatherapp.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    //To get lat lon for city
    @GET("geo/1.0/direct")
    suspend fun getLatLong(
        @Query("q") city: String,
        @Query("appid") appId: String,
        @Query("limit") limit:Int
    ): List<Latlon>

    //To get weather for selected lat lon
    @GET("data/2.5/weather")
    suspend fun getWeatherForLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
    ): WeatherResponse

}