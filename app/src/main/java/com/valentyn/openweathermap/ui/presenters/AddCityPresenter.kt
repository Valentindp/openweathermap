package com.valentyn.openweathermap.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.source.WeatherRepository

@InjectViewState
class AddCityPresenter(val weatherRepository: WeatherRepository) :
    WeatherDataSource.LoadWeatherData<CurrentWeather>, MvpPresenter<AddCityContract>() {

    fun saveCity(cityTitle: String) {
        weatherRepository.getCurrentWeatherByCityName(cityTitle, this)
    }

    override fun onSuccess(successData: CurrentWeather) {
        viewState.showCurrentWeatherList()
    }

    override fun onError(e: Throwable) {
        viewState.showNotFoundError(e.message)
    }
}