package me.kalpanadhurpate.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.kalpanadhurpate.weatherapp.data.api.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://api.openweathermap.org/"

    //Generated API key from OpenWeather.com
    @WeatherApiKey
    @Provides
    fun provideNetworkApiKey(): String = "b897cc6dba22173cd9c8c694db10ee55"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }

}