package com.valentyn.openweathermap.source.remote

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.CurrentWeatherList
import com.valentyn.openweathermap.models.DailyWeatherForecast
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {

    @GET("weather")
    fun getCurrentWeatherByCityName(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    @GET("group")
    fun getCurrentWeatherByArrayCityID(@QueryMap options: Map<String, String>): Call<CurrentWeatherList>


    @GET("forecast/daily")
    fun getDailyWeatherForecastByCityName(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    @GET("forecast/daily")
    fun getDailyWeatherForecastByCityID(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    companion object Factory {
        fun getClient(): WeatherApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .build()

            return retrofit.create(WeatherApi::class.java)
        }
    }
}