package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Coord (
    @field:SerializedName("lat")
    val latitude: Double? = null,

    @field:SerializedName("lon")
    val longitude: Double? = null
){
}