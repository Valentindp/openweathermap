
package com.valentyn.openweathermap.ui.presenters

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.BaseView

@StateStrategyType(SkipStrategy ::class)
interface WeatherContract : BaseView{

        fun showCurrentWeather(currentWeatherList: List<CurrentWeather>)

        fun showAddCity()

        fun showCurrentWeatherDetailsUi(cityId: Int?)

        fun showCurrentWeatherError(message : String?)

        fun showSuccessfullySavedMessage()

       fun showMessageDeleteItem(cityTitle : String)

}
