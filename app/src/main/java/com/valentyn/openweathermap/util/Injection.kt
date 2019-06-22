package com.valentyn.openweathermap.util

import android.content.Context
import com.valentyn.openweathermap.source.WeatherRepository
import com.valentyn.openweathermap.source.local.WeatherDatabase
import com.valentyn.openweathermap.source.local.WeatherLocalDataSource
import com.valentyn.openweathermap.source.remote.WeatherRemoteDataSource

object Injection {
    fun provideWeatherRepository(context: Context): WeatherRepository {
        val database = WeatherDatabase.getInstance(context)
        return WeatherRepository.getInstance(WeatherRemoteDataSource, WeatherLocalDataSource.getInstance(AppExecutors(), database.weatherDao()))
    }
}