package com.valentyn.openweathermap.source

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecast
import com.valentyn.openweathermap.models.DailyWeatherForecastData
import com.valentyn.openweathermap.source.local.WeatherLocalDataSource
import com.valentyn.openweathermap.source.remote.WeatherRemoteDataSource
import java.util.*

class WeatherRepository(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
) : WeatherDataSource {

    var cachedCurrentWeather: LinkedHashMap<Int, CurrentWeather> = LinkedHashMap()
    var cacheIsDirty = false

    override fun getCurrentWeatherByCityName(
        cityName: String,
        callback: WeatherDataSource.LoadWeatherData<CurrentWeather>
    ) {

        weatherRemoteDataSource.getCurrentWeatherByCityName(
            cityName,
            object : WeatherDataSource.LoadWeatherData<CurrentWeather> {
                override fun onSuccess(successData: CurrentWeather) {
                    createCurrentWeather(successData)
                    callback.onSuccess(successData)
                }

                override fun onError(e: Throwable) {
                    callback.onError(e)
                }
            })
    }

    override fun getCurrentWeatherByCityId(
        cityId: Int,
        callback: WeatherDataSource.LoadWeatherData<CurrentWeather>
    ) {
        weatherLocalDataSource.getCurrentWeatherByCityId(
            cityId,
            object : WeatherDataSource.LoadWeatherData<CurrentWeather> {
                override fun onSuccess(successData: CurrentWeather) {
                    callback.onSuccess(successData)
                }

                override fun onError(e: Throwable) {
                    callback.onError(e)
                }
            })
    }

    override fun getCurrentWeatherList(callback: WeatherDataSource.LoadWeatherData<List<CurrentWeather>>) {

        if (cachedCurrentWeather.isNotEmpty() && !cacheIsDirty) {
            callback.onSuccess(ArrayList(cachedCurrentWeather.values))
            return
        }

        if (cacheIsDirty) {
            getCurrentWeatherFromRemoteDataSource(callback)
        } else {
            weatherLocalDataSource.getCurrentWeatherList(object :
                WeatherDataSource.LoadWeatherData<List<CurrentWeather>> {
                override fun onSuccess(successData: List<CurrentWeather>) {
                    refreshCache(successData)
                    callback.onSuccess(ArrayList(cachedCurrentWeather.values))
                }

                override fun onError(e: Throwable) {
                    getCurrentWeatherFromRemoteDataSource(callback)
                }
            })
        }
    }

    private fun getCurrentWeatherFromRemoteDataSource(callback: WeatherDataSource.LoadWeatherData<List<CurrentWeather>>) {

        weatherLocalDataSource.getCurrentWeatherListId(object : WeatherDataSource.LoadWeatherData<List<Int>> {

            override fun onSuccess(successData: List<Int>) {

                weatherRemoteDataSource.getCurrentWeatherList(
                    successData,
                    object : WeatherDataSource.LoadWeatherData<List<CurrentWeather>> {
                        override fun onSuccess(successData: List<CurrentWeather>) {
                            refreshCache(successData)
                            refreshWeatherLocalDataSource(successData)
                            callback.onSuccess(ArrayList(cachedCurrentWeather.values))
                        }

                        override fun onError(e: Throwable) {
                            callback.onError(e)
                        }
                    })
            }

            override fun onError(e: Throwable) {
                callback.onError(e)
            }
        })
    }

    private fun refreshCache(currentWeatherList: List<CurrentWeather>) {
        cachedCurrentWeather.clear()
        currentWeatherList.forEach {
            if (it.cityId != null) cachedCurrentWeather[it.cityId!!] = it
        }
        cacheIsDirty = false
    }


    private fun refreshWeatherLocalDataSource(currentWeatherList: List<CurrentWeather>) {
        weatherLocalDataSource.deleteAllCurrentWeather()
        for (currentWeather in currentWeatherList) {
            weatherLocalDataSource.createCurrentWeather(currentWeather)
        }
    }

    private fun refreshWeatherForecastLocalDataSource(
        cityId: Int,
        DailyWeatherForecastDataList: List<DailyWeatherForecastData>
    ) {
        weatherLocalDataSource.deleteDailyWeatherForecast(cityId)
        for (dailyWeatherForecastData in DailyWeatherForecastDataList) {
            weatherLocalDataSource.createDailyWeatherForecast(dailyWeatherForecastData)
        }
    }

    override fun deleteAllCurrentWeather() {
        weatherLocalDataSource.deleteAllCurrentWeather()
        cachedCurrentWeather.clear()
    }

    fun refreshCurrentWeather() {
        cacheIsDirty = true
    }

    override fun createCurrentWeather(currentWeather: CurrentWeather) {
        weatherLocalDataSource.createCurrentWeather(currentWeather)
        if (currentWeather.cityId != null) cachedCurrentWeather[currentWeather.cityId!!] = currentWeather
    }

    override fun updateCurrentWeather(currentWeather: CurrentWeather) {
        weatherLocalDataSource.updateCurrentWeather(currentWeather)
    }

    override fun deleteCurrentWeather(currentWeatherId: Int) {
        weatherLocalDataSource.deleteCurrentWeather(currentWeatherId)
    }


    override fun getDailyWeatherForecastByCityID(
        cityId: Int,
        callback: WeatherDataSource.LoadWeatherData<List<DailyWeatherForecastData>>
    ) {

        weatherRemoteDataSource.getDailyWeatherForecastList(cityId,
            object : WeatherDataSource.LoadWeatherData<DailyWeatherForecast> {
                override fun onSuccess(successData: DailyWeatherForecast) {
                    val dailyWeatherForecastDataList = getDailyWeatherForecastDataFromDailyWeatherForecast(successData)
                    refreshWeatherForecastLocalDataSource(cityId, dailyWeatherForecastDataList)
                    callback.onSuccess(dailyWeatherForecastDataList)
                }
                override fun onError(e: Throwable) {
                    weatherLocalDataSource.getDailyWeatherForecastByCityID(
                        cityId,
                        object : WeatherDataSource.LoadWeatherData<List<DailyWeatherForecastData>> {
                            override fun onSuccess(successData: List<DailyWeatherForecastData>) {
                                callback.onSuccess(successData)
                            }

                            override fun onError(e: Throwable) {
                                callback.onError(e)
                            }
                        })
                }
            })
    }

    private fun getDailyWeatherForecastDataFromDailyWeatherForecast(dailyWeatherForecast: DailyWeatherForecast): List<DailyWeatherForecastData> {

        val dailyWeatherForecastDataList = mutableListOf<DailyWeatherForecastData>()
        val cityId = dailyWeatherForecast.cityData?.id

        dailyWeatherForecast.dataList?.forEach {
            it?.let {
                it.forecastCityId = cityId
                dailyWeatherForecastDataList.add(it)
            }
        }

        return dailyWeatherForecastDataList
    }

    override fun createDailyWeatherForecast(dailyWeatherForecastData: DailyWeatherForecastData) {
        weatherLocalDataSource.createDailyWeatherForecast(dailyWeatherForecastData)
    }

    override fun updateDailyWeatherForecast(dailyWeatherForecastData: DailyWeatherForecastData) {
        weatherLocalDataSource.updateDailyWeatherForecast(dailyWeatherForecastData)
    }

    override fun deleteAllDailyWeatherForecast() {
        weatherLocalDataSource.deleteAllDailyWeatherForecast()
    }

    override fun deleteDailyWeatherForecast(dailyWeatherForecastDataId: Int) {
        weatherLocalDataSource.deleteDailyWeatherForecast(dailyWeatherForecastDataId)
    }

    companion object {

        private var INSTANCE: WeatherRepository? = null

        @JvmStatic
        fun getInstance(
            weatherRemoteDataSource: WeatherRemoteDataSource,
            weatherLocalDataSource: WeatherLocalDataSource
        ): WeatherRepository {
            return INSTANCE ?: WeatherRepository(weatherRemoteDataSource, weatherLocalDataSource)
                .apply { INSTANCE = this }
        }
    }
}