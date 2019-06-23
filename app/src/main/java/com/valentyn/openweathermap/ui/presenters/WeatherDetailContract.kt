package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.BasePresenter
import com.valentyn.openweathermap.ui.BaseView
import java.util.*

interface WeatherDetailContract {

    interface View : BaseView<Presenter>{

        fun showMissingData()

        fun showCityTitle(cityTitle: String?)

        fun showDate(date: Date?)

        fun showDetails(details: CurrentWeather)

        fun showCurrentTemperature(temp: Double?)

    }

    interface Presenter : BasePresenter{

    }
}