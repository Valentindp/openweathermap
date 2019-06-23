package com.valentyn.openweathermap.models

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.valentyn.openweathermap.models.common.Main
import com.valentyn.openweathermap.models.common.Temp
import com.valentyn.openweathermap.models.common.Weather
import java.util.*

@Entity
class DailyWeatherForecastData(

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    @ColumnInfo(name = "forecastCityId", index = true)
    var forecastCityId: Int? = null,

    @field:SerializedName("dt")
    var dt: Int? = null,

    @Embedded
    @SerializedName("main")
    var main: Main? = null,

    @field:SerializedName("weather")
    var weatherList: List<Weather?>? = null
) {
    val dateTime: Date?
        get() {
            if (dt != null) {
                return Date(dt!!.toLong() * 1000L)
            }
            return null
        }
}