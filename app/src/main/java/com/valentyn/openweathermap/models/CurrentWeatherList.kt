package com.valentyn.openweathermap.models

import com.google.gson.annotations.SerializedName

class CurrentWeatherList {

    @SerializedName("cnt")
    val count: Int? = null

    @SerializedName("list")
    val list: List<CurrentWeather?>? = null
}