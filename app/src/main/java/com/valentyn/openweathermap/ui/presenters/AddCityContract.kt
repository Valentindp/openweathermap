package com.valentyn.openweathermap.ui.presenters

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.valentyn.openweathermap.ui.BaseView

@StateStrategyType(SkipStrategy ::class)
interface AddCityContract : BaseView {

    fun showCurrentWeatherList()

    fun showNotFoundError(message: String?)
}