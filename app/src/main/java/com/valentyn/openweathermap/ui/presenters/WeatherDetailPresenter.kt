package com.valentyn.openweathermap.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.source.WeatherDataSource
import com.valentyn.openweathermap.source.WeatherRepository

@InjectViewState
class WeatherDetailPresenter(
    private val cityId: Int,
    private val weatherRepository: WeatherRepository
) : MvpPresenter<WeatherDetailContract>() {

     fun start() {
        openWeather()
    }

    private fun openWeather(){

        if (cityId == 0){
            viewState.showMissingData()
            return
        }

        weatherRepository.getCurrentWeatherByCityId(cityId, object : WeatherDataSource.LoadWeatherData<CurrentWeather>{
            override fun onSuccess(successData: CurrentWeather) {
                showWeather(successData)
            }

            override fun onError(e: Throwable) {
                viewState.showMissingData()
            }
        })

        weatherRepository.getDailyWeatherForecastByCityID(cityId, object : WeatherDataSource.LoadWeatherData<List<DailyWeatherForecastData>>{
            override fun onSuccess(successData: List<DailyWeatherForecastData>) {
                viewState.showForecastWeather(successData)
            }
            override fun onError(e: Throwable) {
            }
        })

    }

    fun showWeather(currentWeather: CurrentWeather){
        viewState.apply {
            showDate(currentWeather.dateTime)
            showCityTitle(currentWeather.cityName)
            showCurrentTemperature(currentWeather.main?.temp)
            showDetails(currentWeather)
        }

    }

}