
package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.BaseView

interface WeatherContract : BaseView{

        fun showCurrentWeather(currentWeatherList: List<CurrentWeather>)

        fun showAddCity()

        fun showCurrentWeatherDetailsUi(cityId: Int?)

        fun showCurrentWeatherError(message : String?)

        fun showSuccessfullySavedMessage()

       fun showMessageDeleteItem(cityTitle : String)

}
