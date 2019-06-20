package com.valentyn.openweathermap.util

import android.content.Context
import com.valentyn.openweathermap.source.WeatherRepository
import com.valentyn.openweathermap.source.remote.WeatherRemoteDataSource

object Injection {
    fun provideTasksRepository(): WeatherRepository {
        return WeatherRepository.getInstance(WeatherRemoteDataSource)
    }
}