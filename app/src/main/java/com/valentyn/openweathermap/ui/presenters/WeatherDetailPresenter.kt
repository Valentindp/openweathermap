package com.valentyn.openweathermap.ui.presenters

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.source.WeatherRepository

class WeatherDetailPresenter(
    private val cityId: Int,
    private val weatherRepository: WeatherRepository,
    private val weatherDetailView: WeatherDetailContract.View
) : WeatherDetailContract.Presenter {

    init {
        weatherDetailView.presenter = this
    }

    override fun start() {
        openWeather()
    }

    private fun openWeather(){

        if (cityId == 0){
            weatherDetailView.showMissingData()
            return
        }

        weatherRepository.getCurrentWeatherByCityId(cityId, object : WeatherDataSource.LoadWeatherData<CurrentWeather>{
            override fun onSuccess(successData: CurrentWeather) {
                showWeather(successData)
            }

            override fun onError(e: Throwable) {
               weatherDetailView.showMissingData()
            }
        })

    }

    fun showWeather(currentWeather: CurrentWeather){
        weatherDetailView.apply {
            showDate(currentWeather.dateTime)
            showCityTitle(currentWeather.cityName)
            showCurrentTemperature(currentWeather.main?.temp)
            showDetails(currentWeather)
        }

    }

}