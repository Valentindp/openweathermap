package com.valentyn.openweathermap.source

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.remote.WeatherRemoteDataSource
import java.util.LinkedHashMap

class WeatherRepository(val weatherRemoteDataSource: WeatherRemoteDataSource) : WeatherDataSource {

    var cachedTasks: LinkedHashMap<String, CurrentWeather> = LinkedHashMap()

    override fun getCurrentWeatherByCityName(cityName: String, callback: WeatherDataSource.GetCurrentWeatherCallback) {

        weatherRemoteDataSource.getCurrentWeatherByCityName(cityName, object : WeatherDataSource.GetCurrentWeatherCallback {
                override fun onCurrentWeatherLoaded(currentWeather: CurrentWeather) {
                    //refreshCache(tasks)
                    //refreshLocalDataSource(tasks)
                    callback.onCurrentWeatherLoaded(currentWeather)
                }

                override fun onDataNotAvailable(throwable: Throwable) {
                    callback.onDataNotAvailable(throwable)
                }
            })
    }

    override fun getCurrentWeatherList(listId: List<Int>, callback: WeatherDataSource.LoadCurrentWeatherListCallback) {

        weatherRemoteDataSource.getCurrentWeatherList(listId, object : WeatherDataSource.LoadCurrentWeatherListCallback {
            override fun onCurrentWeatherListLoaded(currentWeatherList: List<CurrentWeather>) {
                //refreshCache(tasks)
                //refreshLocalDataSource(tasks)
                callback.onCurrentWeatherListLoaded(currentWeatherList)
            }

            override fun onDataNotAvailable(throwable: Throwable) {
                callback.onDataNotAvailable(throwable)
            }
        })
    }

    override fun createCurrentWeather(currentWeather: CurrentWeather) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCurrentWeather(currentWeatherId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCurrentWeather(currentWeatherId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private var INSTANCE: WeatherRepository? = null

        @JvmStatic fun getInstance(weatherRemoteDataSource: WeatherRemoteDataSource): WeatherRepository {
            return INSTANCE ?: WeatherRepository(weatherRemoteDataSource)
                .apply { INSTANCE = this }
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}