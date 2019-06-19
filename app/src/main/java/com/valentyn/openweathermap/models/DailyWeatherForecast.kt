package com.valentyn.openweathermap.models

import com.google.gson.annotations.SerializedName
import com.valentyn.openweathermap.models.common.City

data class DailyWeatherForecast (
    @field:SerializedName("cod")
    val respCode: String? = null,

    @field:SerializedName("message")
    val message: Double? = null,

    @field:SerializedName("city")
    val cityData: City? = null,

    @field:SerializedName("cnt")
    val dataCount: Int? = null,

    @field:SerializedName("list")
    val dataList: List<DailyWeatherForecastData?>? = null
){
}