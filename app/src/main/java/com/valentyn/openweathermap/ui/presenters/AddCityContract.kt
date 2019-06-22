package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.ui.BasePresenter
import com.valentyn.openweathermap.ui.BaseView

interface AddCityContract {

    interface View : BaseView<Presenter> {

        fun showCurrentWeatherList()

        fun showNotFoundError(message: String?)
    }

    interface Presenter : BasePresenter{

       fun saveCity(cityTitle : String)
    }

}