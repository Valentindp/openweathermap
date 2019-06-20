package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.source.WeatherRepository
import com.valentyn.openweathermap.ui.activitys.WeatherContract

class CurrentWeatherPresenter(val weatherRepository: WeatherRepository, val weatherView: WeatherContract.View) :
    WeatherContract.Presenter {

    private var firstLoad = true

    init {
        weatherView.presenter = this
    }

    override fun start() {
        loadCurrentWeather()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadCurrentWeather(forceUpdate: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCurrentWeather() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openCurrentWeatherDetails(requestedCurrentWeather: CurrentWeather) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun loadCurrentWeather() {

        weatherRepository.getCurrentWeatherByCityName("Днепр", object : WeatherDataSource.GetCurrentWeatherCallback {
            override fun onCurrentWeatherLoaded(currentWeather: CurrentWeather) {
                weatherView.showCurrentWeather(currentWeather)
            }

            override fun onDataNotAvailable(throwable: Throwable) {
                weatherView.let { weatherView.showCurrentWeatherError(throwable.message) }
            }
        })

    }
}