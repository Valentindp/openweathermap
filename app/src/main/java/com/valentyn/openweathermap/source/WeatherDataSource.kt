package com.valentyn.openweathermap.source

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecastData

interface WeatherDataSource {

    interface LoadWeatherData<T> {

        fun onSuccess(successData: T)

        fun onError(e: Throwable)
    }

    fun getCurrentWeatherList(callback: LoadWeatherData<List<CurrentWeather>>)

    fun getCurrentWeatherByCityName(cityName: String, callback: LoadWeatherData<CurrentWeather>)

    fun getCurrentWeatherByCityId(cityId: Int, callback: LoadWeatherData<CurrentWeather>)

    fun getDailyWeatherForecastByCityID(cityId: Int, callback: LoadWeatherData<List<DailyWeatherForecastData>>)


    fun createCurrentWeather(currentWeather: CurrentWeather)

    fun updateCurrentWeather(currentWeather: CurrentWeather)

    fun refreshCurrentWeather()

    fun deleteAllCurrentWeather()

    fun deleteCurrentWeather(currentWeatherId: Int)


    fun createDailyWeatherForecast(dailyWeatherForecastData: DailyWeatherForecastData)

    fun updateDailyWeatherForecast(dailyWeatherForecastData: DailyWeatherForecastData)

    fun deleteAllDailyWeatherForecast()

    fun deleteDailyWeatherForecast(dailyWeatherForecastDataId: Int)

}