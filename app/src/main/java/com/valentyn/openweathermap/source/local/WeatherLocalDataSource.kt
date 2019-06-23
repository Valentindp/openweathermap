package com.valentyn.openweathermap.source.local

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.util.AppExecutors

class WeatherLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val weatherDao: WeatherDao
) : WeatherDataSource {

    private val defaultList = listOf(703448,709930,702550) // id for Lviv, Dnipro, Kyiv

    override fun getCurrentWeatherList(callback: WeatherDataSource.LoadWeatherData<List<CurrentWeather>>) {
        appExecutors.diskIO.execute {
            val currentWeatherList = weatherDao.getCurrentWeather()
            appExecutors.mainThread.execute {
                if (currentWeatherList.isEmpty()) {
                    callback.onError(object : Throwable("No weather data"){})
                } else {
                    callback.onSuccess(currentWeatherList)
                }
            }
        }
    }

    override fun getCurrentWeatherByCityName(cityName: String, callback: WeatherDataSource.LoadWeatherData<CurrentWeather>) {
        // Not required for the local data source
    }

    override fun getCurrentWeatherByCityId(cityId: Int, callback: WeatherDataSource.LoadWeatherData<CurrentWeather>) {
        appExecutors.diskIO.execute {
            val currentWeather = weatherDao.getCurrentWeatherById(cityId)
            appExecutors.mainThread.execute {
                if (currentWeather != null) {
                    callback.onSuccess(currentWeather)
                } else {
                    callback.onError(object : Throwable("No weather data"){})
                }
            }
        }
    }

    fun getCurrentWeatherListId(callback : WeatherDataSource.LoadWeatherData<List<Int>>){
        appExecutors.diskIO.execute {
            val currentWeatherListId = weatherDao.getCurrentWeatherListId()
            appExecutors.mainThread.execute {
                if (currentWeatherListId.isEmpty()) {
                    callback.onSuccess(defaultList)
                } else {
                    callback.onSuccess(currentWeatherListId)
                }
            }
        }
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

    override fun deleteAllCurrentWeather() {
        appExecutors.diskIO.execute { weatherDao.deleteAllCurrentWeather() }
    }


    override fun getDailyWeatherForecastByCityID(cityId: Int, callback: WeatherDataSource.LoadWeatherData<List<DailyWeatherForecastData>>) {
        appExecutors.diskIO.execute {
            val DailyWeatherForecastList = weatherDao.getDailyWeatherForecastDatatByCityId()
            appExecutors.mainThread.execute {
                if (DailyWeatherForecastList.isEmpty()) {
                    callback.onError(object : Throwable("No weather data"){})
                } else {
                    callback.onSuccess(DailyWeatherForecastList)
                }
            }
        }
    }

    override fun createDailyWeatherForecast(dailyWeatherForecastData: DailyWeatherForecastData) {
        appExecutors.diskIO.execute { weatherDao.insertDailyWeatherForecastData(dailyWeatherForecastData) }
    }

    override fun updateDailyWeatherForecast(dailyWeatherForecastData: DailyWeatherForecastData) {
        appExecutors.diskIO.execute { weatherDao.updateDailyWeatherForecastData(dailyWeatherForecastData) }
    }

    override fun deleteAllDailyWeatherForecast() {
        appExecutors.diskIO.execute { weatherDao.deleteAllDailyWeatherForecastData() }
    }

    override fun deleteDailyWeatherForecast(dailyWeatherForecastDataId: Int) {
        appExecutors.diskIO.execute { weatherDao.deleteDailyWeatherForecastDatatById(dailyWeatherForecastDataId) }
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