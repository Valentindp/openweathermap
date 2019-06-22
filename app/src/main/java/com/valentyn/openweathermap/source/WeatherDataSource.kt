package com.valentyn.openweathermap.source

import com.valentyn.openweathermap.models.CurrentWeather

interface WeatherDataSource {

    interface LoadCurrentWeatherListCallback {

        fun onCurrentWeatherListLoaded(currentWeatherList: List<CurrentWeather>)

        fun onDataNotAvailable(throwable: Throwable)
    }

    interface GetCurrentWeatherCallback {

        fun onCurrentWeatherLoaded(currentWeather: CurrentWeather)

        fun onDataNotAvailable(throwable: Throwable)
    }

    interface GetCurrentWeatherListIdCallback {

        fun onCurrentWeatherListIdLoaded(currentWeatherListId: List<Int>)

    }

    fun getCurrentWeatherList(callback: LoadCurrentWeatherListCallback)

    fun getCurrentWeatherByCityName(cityName: String, callback: GetCurrentWeatherCallback)


    fun createCurrentWeather(currentWeather: CurrentWeather)

    fun updateCurrentWeather(currentWeather: CurrentWeather)

    fun refreshCurrentWeather()

    fun deleteAllCurrentWeather()

    fun deleteCurrentWeather(currentWeatherId: Int)

}