package com.valentyn.openweathermap.models

import com.google.gson.annotations.SerializedName
import com.valentyn.openweathermap.models.common.Temp
import com.valentyn.openweathermap.models.common.Weather

class DailyWeatherForecastData(
    @field:SerializedName("dt")
    private val dt: Int? = null,

    @field:SerializedName("temp")
    val tempData: Temp? = null,

    @field:SerializedName("pressure")
    val pressure: Double? = null,

    @field:SerializedName("humidity")
    val humidity: Double? = null,

    @field:SerializedName("weather")
    val weatherList: List<Weather?>? = null
) {
}