package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Wind (
    @field:SerializedName("speed")
    val speed: Double? = null,

    @field:SerializedName("deg")
    val degree: Double? = null
){
}