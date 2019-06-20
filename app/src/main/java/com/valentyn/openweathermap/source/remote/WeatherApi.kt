package com.valentyn.openweathermap.source.remote

import com.valentyn.openweathermap.models.CurrentWeather
import com.valentyn.openweathermap.models.DailyWeatherForecast
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {

    @GET("weather")
    fun getCurrentWeatherByCityName(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    @GET("weather")
    fun getCurrentWeatherByCityID(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    @GET("weather")
    fun getCurrentWeatherByGeoCoordinates(@QueryMap options: Map<String, String>): Call<CurrentWeather>

    @GET("weather")
    fun getCurrentWeatherByZipCode(@QueryMap options: Map<String, String>): Call<CurrentWeather>


    @GET("forecast/daily")
    fun getDailyWeatherForecastByCityName(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    @GET("forecast/daily")
    fun getDailyWeatherForecastByCityID(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    @GET("forecast/daily")
    fun getDailyWeatherForecastByGeoCoordinates(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    @GET("forecast/daily")
    fun getDailyWeatherForecastByZipCode(@QueryMap options: Map<String, String>): Call<DailyWeatherForecast>

    companion object Factory {
        fun getClient(): WeatherApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org")
                .build()

            return retrofit.create(WeatherApi::class.java);
        }
    }
}