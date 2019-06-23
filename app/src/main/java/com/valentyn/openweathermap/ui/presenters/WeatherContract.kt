
package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.BasePresenter
import com.valentyn.openweathermap.ui.BaseView

interface WeatherContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showCurrentWeather(currentWeatherList: List<CurrentWeather>)

        fun showAddCity()

        fun showCurrentWeatherDetailsUi(cityId: Int?)

        fun showCurrentWeatherError(message : String?)

        fun showSuccessfullySavedMessage()

       fun showMessageDeleteItem(cityTitle : String)

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadCurrentWeather(forceUpdate: Boolean)

        fun addNewCity()

        fun deleteCity(currentWeather: CurrentWeather)


        fun openCurrentWeatherDetails(requestedCurrentWeather: CurrentWeather)
    }
}
