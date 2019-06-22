package com.valentyn.openweathermap.ui.presenters

import android.app.Activity
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.source.WeatherRepository
import com.valentyn.openweathermap.ui.activitys.AddCityActivity

class CurrentWeatherPresenter(val weatherRepository: WeatherRepository, val weatherView: WeatherContract.View) :
    WeatherContract.Presenter {

    private var firstLoad = true

    init {
        weatherView.presenter = this
    }

    override fun start() {
        loadCurrentWeather(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
        if (AddCityActivity.REQUEST_ADD_CITY ==
            requestCode && Activity.RESULT_OK == resultCode
        ) {
            weatherView.showSuccessfullySavedMessage()
        }
    }

    override fun loadCurrentWeather(forceUpdate: Boolean) {
        loadCurrentWeather(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    fun loadCurrentWeather(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (forceUpdate) {
            weatherRepository.refreshCurrentWeather()
        }

        weatherRepository.getCurrentWeatherList(object : WeatherDataSource.LoadCurrentWeatherListCallback {
            override fun onCurrentWeatherListLoaded(currentWeatherList: List<CurrentWeather>) {
                weatherView.showCurrentWeather(currentWeatherList)
            }

            override fun onDataNotAvailable(throwable: Throwable) {
                weatherView.let { weatherView.showCurrentWeatherError(throwable.message) }
            }
        })
    }


    override fun addNewCity() {
        weatherView.showAddCity()
    }

    override fun openCurrentWeatherDetails(requestedCurrentWeather: CurrentWeather) {
        weatherView.showCurrentWeatherDetailsUi(requestedCurrentWeather.cityId)
    }


}