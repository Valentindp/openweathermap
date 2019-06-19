package com.valentyn.openweathermap.models.common

import com.google.gson.annotations.SerializedName

data class Snow (
    @field:SerializedName("3h")
    val snowVol3h: Double? = null
){
}