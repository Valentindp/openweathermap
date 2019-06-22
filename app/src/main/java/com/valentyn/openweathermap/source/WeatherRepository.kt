package com.valentyn.openweathermap.source

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.local.WeatherLocalDataSource
import com.valentyn.openweathermap.source.remote.WeatherRemoteDataSource
import java.util.*

class WeatherRepository(val weatherRemoteDataSource: WeatherRemoteDataSource, val weatherLocalDataSource : WeatherLocalDataSource) : WeatherDataSource {

    var cachedCurrentWeather: LinkedHashMap<Int, CurrentWeather> = LinkedHashMap()
    var cacheIsDirty = false

    override fun getCurrentWeatherByCityName(cityName: String, callback: WeatherDataSource.GetCurrentWeatherCallback) {

        weatherRemoteDataSource.getCurrentWeatherByCityName(cityName, object : WeatherDataSource.GetCurrentWeatherCallback {
                override fun onCurrentWeatherLoaded(currentWeather: CurrentWeather) {
                    createCurrentWeather(currentWeather)
                    callback.onCurrentWeatherLoaded(currentWeather)
                }

                override fun onDataNotAvailable(throwable: Throwable) {
                    callback.onDataNotAvailable(throwable)
                }
            })
    }

    override fun getCurrentWeatherList(callback: WeatherDataSource.LoadCurrentWeatherListCallback) {

        if (cachedCurrentWeather.isNotEmpty() && !cacheIsDirty) {
            callback.onCurrentWeatherListLoaded(ArrayList(cachedCurrentWeather.values))
            return
        }

        if (cacheIsDirty) {
            getCurrentWeatherFromRemoteDataSource(callback)
        } else {
            weatherLocalDataSource.getCurrentWeatherList(object : WeatherDataSource.LoadCurrentWeatherListCallback {
                override fun onCurrentWeatherListLoaded(currentWeatherList: List<CurrentWeather>) {
                    refreshCache(currentWeatherList)
                    callback.onCurrentWeatherListLoaded(ArrayList(cachedCurrentWeather.values))
                }

                override fun onDataNotAvailable(throwable: Throwable) {
                    getCurrentWeatherFromRemoteDataSource(callback)
                }
            })
        }
    }

    private fun getCurrentWeatherFromRemoteDataSource(callback: WeatherDataSource.LoadCurrentWeatherListCallback) {

        weatherLocalDataSource.getCurrentWeatherListId(object : WeatherDataSource.GetCurrentWeatherListIdCallback{
            override fun onCurrentWeatherListIdLoaded(currentWeatherListId: List<Int>) {

                weatherRemoteDataSource.getCurrentWeatherList(currentWeatherListId, object : WeatherDataSource.LoadCurrentWeatherListCallback {
                    override fun onCurrentWeatherListLoaded(currentWeatherList: List<CurrentWeather>) {
                        refreshCache(currentWeatherList)
                        refreshLocalDataSource(currentWeatherList)
                        callback.onCurrentWeatherListLoaded(ArrayList(cachedCurrentWeather.values))
                    }
                    override fun onDataNotAvailable(throwable: Throwable) {
                        callback.onDataNotAvailable(throwable)
                    }
                })
            }
        })
    }

    private fun refreshCache(currentWeatherList: List<CurrentWeather>) {
        cachedCurrentWeather.clear()
        currentWeatherList.forEach {
            if (it.cityId != null) cachedCurrentWeather[it.cityId!!] = it
        }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(currentWeatherList: List<CurrentWeather>) {
        weatherLocalDataSource.deleteAllCurrentWeather()
        for (currentWeather in currentWeatherList) {
            weatherLocalDataSource.createCurrentWeather(currentWeather)
        }
    }

    override fun deleteAllCurrentWeather() {
        weatherLocalDataSource.deleteAllCurrentWeather()
        cachedCurrentWeather.clear()
    }

    override fun refreshCurrentWeather() {
        cacheIsDirty = true
    }

    override fun createCurrentWeather(currentWeather: CurrentWeather) {
        weatherLocalDataSource.createCurrentWeather(currentWeather)
        if (currentWeather.cityId != null) cachedCurrentWeather[currentWeather.cityId!!] = currentWeather
    }

    override fun updateCurrentWeather(currentWeather: CurrentWeather) {
        weatherLocalDataSource.updateCurrentWeather(currentWeather)
    }

    override fun deleteCurrentWeather(currentWeatherId: Int) {
        weatherLocalDataSource.deleteCurrentWeather(currentWeatherId)
    }

    companion object {

        private var INSTANCE: WeatherRepository? = null

        @JvmStatic fun getInstance(weatherRemoteDataSource: WeatherRemoteDataSource, weatherLocalDataSource : WeatherLocalDataSource): WeatherRepository {
            return INSTANCE ?: WeatherRepository(weatherRemoteDataSource, weatherLocalDataSource)
                .apply { INSTANCE = this }
        }
    }
}