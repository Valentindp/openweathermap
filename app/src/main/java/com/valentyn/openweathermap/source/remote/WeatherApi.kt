package com.valentyn.openweathermap.source.remote

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {

    //Current Weather Endpoints start
    @GET("weather")
    fun getCurrentWeatherByCityName(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    @GET("weather")
    fun getCurrentWeatherByCityID(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    @GET("weather")
    fun getCurrentWeatherByGeoCoordinates(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    @GET("weather")
    fun getCurrentWeatherByZipCode(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    //Current Weather Endpoints end

    //Three hour forecast endpoints start
    @GET("forecast/daily")
    fun getDailyWeatherForecastByCityName(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    @GET("forecast/daily")
    fun getDailyWeatherForecastByCityID(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    @GET("forecast/daily")
    fun getDailyWeatherForecastByGeoCoordinates(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    @GET("forecast/daily")
    fun getDailyWeatherForecastByZipCode(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

}