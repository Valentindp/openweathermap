package com.valentyn.openweathermap.ui.presenters

import android.app.Activity
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.source.WeatherRepository
import com.valentyn.openweathermap.ui.activitys.AddCityActivity

@InjectViewState
class WeatherPresenter(val weatherRepository: WeatherRepository) : MvpPresenter<WeatherContract>() {

    private var firstLoad = true

    fun start() {
        loadCurrentWeather(firstLoad)
        firstLoad = false
    }

    fun result(requestCode: Int, resultCode: Int) {
        if (AddCityActivity.REQUEST_ADD_CITY ==
            requestCode && Activity.RESULT_OK == resultCode
        ) {
            viewState.showSuccessfullySavedMessage()
        }
    }

    fun loadCurrentWeather(forceUpdate: Boolean) {
        if (forceUpdate) {
            weatherRepository.refreshCurrentWeather()
        }

        weatherRepository.getCurrentWeatherList(object : WeatherDataSource.LoadWeatherData<List<CurrentWeather>> {
            override fun onSuccess(successData: List<CurrentWeather>) {
                viewState.showCurrentWeather(successData)
            }

            override fun onError(e: Throwable) {
                viewState.showCurrentWeatherError(e.message)
            }
        })
    }

    fun addNewCity() {
        viewState.showAddCity()
    }

    fun deleteCity(currentWeather: CurrentWeather) {
        currentWeather.cityId?.let {
            weatherRepository.deleteCurrentWeather(it)
            weatherRepository.deleteDailyWeatherForecast(it)
        }
        currentWeather.cityName?.let { viewState.showMessageDeleteItem(it) }
    }

    fun openCurrentWeatherDetails(requestedCurrentWeather: CurrentWeather) {
        viewState.showCurrentWeatherDetailsUi(requestedCurrentWeather.cityId)
    }
}