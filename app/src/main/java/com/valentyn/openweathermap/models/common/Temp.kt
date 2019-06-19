package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Temp (
    @field:SerializedName("day")
    val tempDay: Double? = null,

    @field:SerializedName("min")
    val tempMin: Double? = null,

    @field:SerializedName("max")
    val tempMax: Double? = null,

    @field:SerializedName("night")
    val tempNight: Double? = null,

    @field:SerializedName("eve")
    val tempEvening: Double? = null,

    @field:SerializedName("morn")
    val tempMorning: Double? = null
){
}