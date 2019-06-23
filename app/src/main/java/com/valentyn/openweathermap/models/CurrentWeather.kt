package com.valentyn.openweathermap.models

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.valentyn.openweathermap.models.common.*
import java.util.*

@Entity
data class CurrentWeather constructor(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var cityId: Int? = null,

    @field:SerializedName("name")
    var cityName: String? = null,

    @Embedded
    @SerializedName("main")
    var main: Main? = null,

    @Ignore
    @field:SerializedName("sys")
    var systemData: System? = null,

    @field:SerializedName("dt")
    var dt: Int? = null,

    @Ignore
    @field:SerializedName("coord")
    val coordData: Coord? = null,

    @field:SerializedName("weather")
    var weatherList: List<Weather?>? = null,

    @Ignore
    @field:SerializedName("base")
    val baseStation: String? = null,

    @Ignore
    @SerializedName("visibility")
    val visibility: Int? = null,

    @Ignore
    @field:SerializedName("wind")
    val windData: Wind? = null,

    @Ignore
    @field:SerializedName("rain")
    val rainData: Rain? = null,

    @Ignore
    @field:SerializedName("clouds")
    val cloudData: Cloud? = null,

    @Ignore
    @field:SerializedName("cod")
    val respCode: Int? = null

){
    val dateTime: Date?
        get() {
            if (dt != null ) {
                return Date(dt!!.toLong() * 1000L )
            }
            return null
        }
}