
package com.valentyn.openweathermap.ui.activitys

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.ui.BasePresenter
import com.valentyn.openweathermap.ui.BaseView

interface WeatherContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showCurrentWeather(tasks: List<CurrentWeather>)

        fun showAddCurrentWeather()

        fun showCurrentWeatherDetailsUi(cityId: Int)

        fun showCurrentWeatherError()

        fun showNoCurrentWeather()

        fun showSuccessfullySavedMessage()

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadCurrentWeather(forceUpdate: Boolean)

        fun addCurrentWeather()

        fun openCurrentWeatherDetails(requestedCurrentWeather: CurrentWeather)
    }
}
