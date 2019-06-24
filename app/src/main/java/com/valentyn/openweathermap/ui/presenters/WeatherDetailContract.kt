package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.ui.BaseView
import java.util.*

interface WeatherDetailContract : BaseView {

        fun showMissingData()

        fun showCityTitle(cityTitle: String?)

        fun showDate(date: Date?)

        fun showDetails(details: CurrentWeather)

        fun showCurrentTemperature(temp: Double?)

        fun showForecastWeather(list:List<DailyWeatherForecastData>)

}