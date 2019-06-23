package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.source.WeatherRepository

class AddCityPresenter(val weatherRepository: WeatherRepository, val addCityView: AddCityContract.View) :
    AddCityContract.Presenter, WeatherDataSource.LoadWeatherData<CurrentWeather> {

    init {
        addCityView.presenter = this
    }

    override fun start() {
        // Not required
    }


    override fun saveCity(cityTitle: String) {
        weatherRepository.getCurrentWeatherByCityName(cityTitle, this)
    }

    override fun onSuccess(successData: CurrentWeather) {
        addCityView.showCurrentWeatherList()
    }

    override fun onError(e: Throwable) {
        addCityView.showNotFoundError(e.message)
    }




}