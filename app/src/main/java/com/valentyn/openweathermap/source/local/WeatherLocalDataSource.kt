package com.valentyn.openweathermap.source.local

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.util.AppExecutors

class WeatherLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val weatherDao: WeatherDao
) : WeatherDataSource {

    private val defaultList = listOf(703448,709930,702550) // id for Lviv, Dnipro, Kyiv

    override fun getCurrentWeatherList(callback: WeatherDataSource.LoadCurrentWeatherListCallback) {
        appExecutors.diskIO.execute {
            val currentWeatherList = weatherDao.getCurrentWeather()
            appExecutors.mainThread.execute {
                if (currentWeatherList.isEmpty()) {
                    callback.onDataNotAvailable(object : Throwable("No weather data"){})
                } else {
                    callback.onCurrentWeatherListLoaded(currentWeatherList)
                }
            }
        }
    }

    override fun getCurrentWeatherByCityName(cityName: String, callback: WeatherDataSource.GetCurrentWeatherCallback) {
        // Not required for the local data source
    }

    override fun createCurrentWeather(currentWeather: CurrentWeather) {
        appExecutors.diskIO.execute { weatherDao.insertCurrentWeather(currentWeather) }
    }

    override fun updateCurrentWeather(currentWeather: CurrentWeather) {
        appExecutors.diskIO.execute { weatherDao.updateCurrentWeather(currentWeather) }
    }

    override fun deleteCurrentWeather(currentWeatherId: Int) {
        appExecutors.diskIO.execute { weatherDao.deleteCurrentWeatherById(currentWeatherId) }
    }

    override fun refreshCurrentWeather() {
        // Not required
    }

    override fun deleteAllCurrentWeather() {
        appExecutors.diskIO.execute { weatherDao.deleteCurrentWeather() }
    }

    fun getCurrentWeatherListId(callback : WeatherDataSource.GetCurrentWeatherListIdCallback){
        appExecutors.diskIO.execute {
            val currentWeatherListId = weatherDao.getCurrentWeatherListId()
            appExecutors.mainThread.execute {
                if (currentWeatherListId.isEmpty()) {
                    callback.onCurrentWeatherListIdLoaded(defaultList)
                } else {
                    callback.onCurrentWeatherListIdLoaded(currentWeatherListId)
                }
            }
        }
    }

    companion object {
        private var INSTANCE: WeatherLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, weatherDao: WeatherDao): WeatherLocalDataSource {
            if (INSTANCE == null) {
                synchronized(WeatherLocalDataSource::javaClass) {
                    INSTANCE = WeatherLocalDataSource(appExecutors, weatherDao)
                }
            }
            return INSTANCE!!
        }


    }
}