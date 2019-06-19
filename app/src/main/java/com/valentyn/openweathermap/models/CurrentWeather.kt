package com.valentyn.openweathermap.models

import com.google.gson.annotations.SerializedName
import com.valentyn.openweathermap.models.common.*
import java.lang.System
import java.util.*

data class CurrentWeather (
    @field:SerializedName("dt")
    private val dt: Int? = null,

    @field:SerializedName("rain")
    val rainData: Rain? = null,

    @field:SerializedName("snow")
    val snowData: Snow? = null,

    @field:SerializedName("coord")
    val coordData: Coord? = null,

    @field:SerializedName("weather")
    val weatherList: List<Weather?>? = null,

    @field:SerializedName("name")
    val cityName: String? = null,

    @field:SerializedName("cod")
    val respCode: Int? = null,

    @field:SerializedName("main")
    val mainData: Main? = null,

    @field:SerializedName("clouds")
    val cloudData: Cloud? = null,

    @field:SerializedName("id")
    val cityId: Int? = null,

    @field:SerializedName("sys")
    val systemData: System? = null,

    @field:SerializedName("base")
    val baseStation: String? = null,

    @field:SerializedName("wind")
    val windData: Wind? = null
){
    val dateTime: Date?
        get() {
            if (dt != null) {
                return Date(dt.toLong() * 1000L)
            }
            return null
        }
}