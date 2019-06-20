package com.valentyn.openweathermap.source

import com.valentyn.openweathermap.models.CurrentWeather

interface WeatherDataSource {

    interface LoadCurrentWeatherAllCallback {

        fun onCurrentWeatherAllLoaded(currentWeatherAll: List<CurrentWeather>)

        fun onDataNotAvailable()
    }

    interface GetCurrentWeatherCallback {

        fun onCurrentWeatherLoaded(currentWeather: CurrentWeather)

        fun onDataNotAvailable(throwable: Throwable)
    }

    fun getCurrentWeatherAll(callback: LoadCurrentWeatherAllCallback)

    fun getCurrentWeatherByCityId(currentWeatherId: Int, callback: GetCurrentWeatherCallback)

    fun getCurrentWeatherByCityName(cityName: String, callback: GetCurrentWeatherCallback)


    fun createCurrentWeather(currentWeather: CurrentWeather)

    fun updateCurrentWeather(currentWeatherId: Int)

    fun deleteCurrentWeather(currentWeatherId: Int)

}